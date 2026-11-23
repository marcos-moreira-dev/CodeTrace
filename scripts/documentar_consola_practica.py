#!/usr/bin/env python3
"""Anota cada fuente de la biblioteca CodeTrace con ejecución y salida honesta.

Uso desde raíz del repositorio:
    python3 scripts/documentar_consola_practica.py --write
    python3 scripts/documentar_consola_practica.py --check

No modifica algoritmos. No inventa salidas de componentes sin punto de entrada.
"""
from __future__ import annotations

import argparse
import json
import re
import sys
from collections import Counter
from pathlib import Path

BEGIN = 'CodeTrace | EJEMPLO DE CONSOLA (practica)'
END = 'CodeTrace | FIN EJEMPLO DE CONSOLA'
SKIP = {'catalog.tsv'}
EXT = {'.java','.kt','.cs','.ts','.js','.py','.c','.h','.cpp','.hpp','.go','.rb','.ps1','.sh','.cmd','.asm'}
MARKERS = re.compile(r'Salida esperada(?:[^:\n]{0,65})?:\s*', re.I)
GENERIC = re.compile(r'(?i)se imprime el valor|se imprime la colecci[oó]n|salida del cliente|valores variables|completan los fragmentos|resultados del cliente|ver la salida real|salida del programa por verificar')


def comment_style(path: Path) -> str:
    if path.suffix in {'.java','.kt','.cs','.ts','.js','.c','.h','.cpp','.hpp','.go'}: return 'block'
    if path.suffix == '.asm': return ';'
    if path.suffix == '.cmd': return 'REM'
    return '#'


def strip_generated(src: str, path: Path) -> str:
    if BEGIN not in src: return src
    st=comment_style(path)
    if st=='block':
        pat=r'\n?/\*\n\s*\*?\s*'+re.escape(BEGIN)+r'.*?'+re.escape(END)+r'\s*\*/\s*\Z'
    else:
        marker='(?:'+re.escape(st)+r'\s*)'
        pat=r'\n'+marker+re.escape(BEGIN)+r'.*?'+marker+re.escape(END)+r'\s*\Z'
    found=re.search(pat,src,re.S)
    if not found: raise ValueError('Marcadores de anotacion alterados: '+str(path))
    return src[:found.start()].rstrip()+'\n'


def remove_legacy_generic_output(src: str, path: Path) -> str:
    # Reemplazar las salidas de relleno: el ejemplo final ofrece el valor concreto.
    if path.suffix in {'.java','.kt','.cs','.ts','.js','.c','.h','.cpp','.hpp','.go'}:
        pattern = re.compile(r'\n/\*\s*(?:\*\s*)?Salida esperada[^\n]*\n.*?\*/\s*',re.S|re.I)
    else:
        leader = re.escape(comment_style(path))
        pattern = re.compile(r'\n(?:'+leader+r'\s*Salida esperada[^\n]*\n)(?:'+leader+r'[^\n]*\n?)*',re.I)
    def choose(m):
        return '\n' if GENERIC.search(m.group()) or 'Sin salida de texto; las pruebas' in m.group() else m.group()
    result = pattern.sub(choose,src)
    # Hay algunos clientes Go con comentarios originales // a pesar de utilizar "block" en el pie nuevo.
    result = re.sub(r'\n//\s*Salida esperada[^\n]*\n(?://[^\n]*\n?)*',
                    lambda m: '\n' if GENERIC.search(m.group()) else m.group(), result, flags=re.I)
    return result


def expected_output(src: str) -> list[str] | None:
    # Anotaciones originales: comentarios de salida al final del cliente.
    lines=src.splitlines()
    starts=[i for i,s in enumerate(lines) if MARKERS.search(s)]
    if not starts: return None
    i=starts[-1]
    m=MARKERS.search(lines[i])
    inline=lines[i][m.end():].strip()
    acc=[inline] if inline else []
    for line in lines[i+1:]:
        stripped=line.strip()
        if stripped in ('*/','# FIN',''): break
        stripped=re.sub(r'^(?:\*\s?|#\s?|;\s?|REM\s?)','',stripped,flags=re.I)
        if stripped.startswith('CodeTrace |'): break
        if not stripped: break
        acc.append(stripped)
        if len(acc)>=32: break
    acc=[a for a in acc if a and not GENERIC.search(a)]
    if not acc: return None
    if any(re.search(r'^(?:nota|observa|el cliente)',a,re.I) for a in acc[:1]): return None
    return acc


def entrypoint(path:Path, source:str) -> bool:
    ext=path.suffix
    if ext in {'.ps1','.sh','.cmd'}:
        if path.stem.lower() in {'funciones','eventos'} and path.name.lower() not in {'funciones.ps1'}: return False
        return True
    if ext in {'.h','.hpp'}: return False
    if ext=='.asm': return bool(re.search(r'(?m)^\s*(?:global|GLOBAL)\s+_start\b',source))
    if ext=='.java': return bool(re.search(r'\bstatic\s+void\s+main\s*\(',source))
    if ext=='.kt': return bool(re.search(r'\bfun\s+main\s*\(',source))
    if ext=='.cs': return bool(re.search(r'\b(?:void|Task)\s+Main\s*\(',source) or re.search(r'(?m)^\s*Console\.(?:WriteLine|Write)\s*\(',source))
    if ext in {'.c','.cpp'}: return bool(re.search(r'\bmain\s*\(',source))
    if ext=='.go': return bool(re.search(r'\bfunc\s+main\s*\(',source))
    if ext=='.py': return bool(path.stem.lower().startswith('main') or re.search(r'__name__\s*==\s*[\'\"]__main__[\'\"]',source) or re.search(r'(?m)^print\s*\(',source))
    if ext=='.rb': return bool(path.stem.lower().startswith('main') or re.search(r'(?m)^(?:puts|print|p)\b',source) or '$PROGRAM_NAME' in source)
    if ext in {'.js','.ts'}: return bool(path.stem.lower().startswith('main') or re.search(r'(?m)^\s*console\.(?:log|error)\s*\(',source) and 'export ' not in source or re.search(r'\b(?:require\.main|import\.meta)\b',source))
    return False


def output_from_cache(path: Path, pack: Path, cache: dict) -> list[str] | None:
    key=path.relative_to(pack).as_posix()
    result=cache.get(key)
    if isinstance(result,str):
        return result.splitlines() if result else ['(el programa termina sin imprimir texto)']
    return None


def select_client(path:Path, sources: dict[Path,str]) -> Path | None:
    mates=[p for p,s in sources.items() if p.parent==path.parent and entrypoint(p,s)]
    if not mates: return None
    pref=sorted(mates,key=lambda p:(0 if p.stem.lower().startswith('main') or p.stem.lower()=='main' else 1,p.name.lower()))
    return pref[0]


def command(path:Path, root:Path, client:Path|None) -> tuple[list[str],bool]:
    lang=path.relative_to(root).parts[0]
    target=client or path
    rel=target.relative_to(root/lang).as_posix().replace('/','\\')
    dir_rel=target.parent.relative_to(root/lang).as_posix().replace('/','\\')
    ext=target.suffix
    executable=client is not None or lang in {'powershell','shell','cmd'}
    if lang=='java21':
        # Compilar paquete completo evita errores de referencias cruzadas.
        clazz=target.stem
        source_file=target.read_text(encoding='utf-8')
        match=re.search(r'(?m)^\s*package\s+([\w.]+)\s*;',source_file)
        if match: clazz=match.group(1)+'.'+clazz
        return ([r'$out=Join-Path $env:TEMP "ct-practica-java"',
            r'New-Item -ItemType Directory -Force -Path $out | Out-Null',
            r'javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName',
            'java -cp $out '+clazz] if executable else [r'$out=Join-Path $env:TEMP "ct-practica-java"',r'New-Item -ItemType Directory -Force -Path $out | Out-Null',r'javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName']),executable
    if lang=='kotlin':
        # Incluir la implementación del cliente, pero no múltiples `main` en un mismo JAR.
        related=sorted(target.parent.glob('*.kt'))
        mains=[p for p in related if entrypoint(p,p.read_text(encoding='utf-8'))]
        if len(mains)<=1:
            sources=f'.\\{dir_rel}\\*.kt'
        else:
            sources=' '.join(f'.\\{p.relative_to(root/lang).as_posix().replace(chr(47),chr(92))}' for p in related if p==target or p not in mains)
        commands=[f'kotlinc {sources} -jvm-target 21 -include-runtime -d "$env:TEMP\\ct-kotlin.jar"']
        if executable: commands.append('java -jar "$env:TEMP\\ct-kotlin.jar"')
        return commands,executable
    if lang=='csharp':
        return ([r'$d=Join-Path $env:TEMP "ct-ejemplo-csharp"',
             'if (Test-Path $d) { Remove-Item $d -Recurse -Force }',
             'dotnet new console -f net8.0 -o $d --force',
             'Remove-Item "$d\\Program.cs" -ErrorAction SilentlyContinue',
             f'Copy-Item .\\{dir_rel}\\*.cs $d -Force']+
             (['dotnet run --project $d'] if executable else ['dotnet build $d'])),executable
    if lang=='typescript':
        cmds=[f'tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\\ct-ejemplo-ts" .\\{rel}']
        if executable: cmds.append(f'node "$env:TEMP\\ct-ejemplo-ts\\{target.stem}.js"')
        return cmds,executable
    if lang=='javascript': return [f'node .\\{rel}'] if executable else [f'node --check .\\{rel}'], executable
    if lang=='python': return [f'python -B .\\{rel}'] if executable else [f'python -c "import ast,pathlib; ast.parse(pathlib.Path(r\'{rel}\').read_text(encoding=\'utf-8\'))"'],executable
    if lang=='ruby': return [f'ruby .\\{rel}'] if executable else [f'ruby -c .\\{rel}'],executable
    if lang=='c':
        cmd=f'gcc -std=c17 -Wall -Wextra .\\{dir_rel}\\*.c -o "$env:TEMP\\ct-ejemplo-c.exe"' if executable else f'gcc -std=c17 -fsyntax-only .\\{rel}'
        return [cmd]+(['& "$env:TEMP\\ct-ejemplo-c.exe"'] if executable else []),executable
    if lang=='cpp':
        if executable: return [f'g++ -std=c++20 -Wall -Wextra .\\{dir_rel}\\*.cpp -o "$env:TEMP\\ct-ejemplo-cpp.exe"','& "$env:TEMP\\ct-ejemplo-cpp.exe"'],True
        return [f'g++ -std=c++20 -fsyntax-only .\\{rel}'],False
    if lang=='go':
        if executable: return [f'go run (Get-ChildItem .\\{dir_rel}\\*.go).FullName'],True
        return [f'go test .\\{rel}'],False
    if lang=='powershell': return [f'pwsh -NoProfile -File .\\{rel}'],True
    if lang=='shell':
        original=target.read_text(encoding="utf-8")
        interpreter="bash" if "#!/usr/bin/env bash" in original or "source " in original or "pipefail" in original else "sh"
        return [f'wsl {interpreter} {target.relative_to(root/lang).as_posix()}'],True
    if lang=='cmd': return [f'cmd /c .\\{rel}'],True
    if lang=='nasm-x64':
        cm=[f'wsl nasm -f elf64 {rel.replace(chr(92),chr(47))} -o /tmp/ct-ejemplo.o']
        if executable: cm += ['wsl ld /tmp/ct-ejemplo.o -o /tmp/ct-ejemplo', "wsl sh -c '/tmp/ct-ejemplo; printf \"codigo-de-salida:%s\\n\" \"$?\"'"]
        return cm,executable
    return ['(sin comando)'],False


def render(path:Path,src:str,pack:Path,sources:dict[Path,str],cache:dict) -> tuple[str,str]:
    original=strip_generated(src,path)
    st=comment_style(path)
    self_entry=entrypoint(path,original)
    client=path if self_entry else select_client(path,sources)
    cmds,exe=command(path,pack,client)
    source_out=None
    if client is not None:
        source_out=output_from_cache(client,pack,cache) or expected_output(strip_generated(sources[client],client))
    if source_out is not None:
        source_out=[line for line in source_out if not GENERIC.search(line)]
    if not source_out:
        source_out=['(sin salida de consola; la comprobación termina correctamente)'] if not exe else ['(salida del programa por verificar con el entorno indicado)']
    lines=[BEGIN,'', 'Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):']
    lines.extend('  '+c for c in cmds)
    if not self_entry and client is not None: lines.append(f'Nota: {path.name} es un componente; la salida corresponde al cliente {client.name}.')
    elif not exe: lines.append('Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.')
    lines.append('Salida esperada:')
    lines.extend('  '+v for v in source_out[:35])
    lines.append(END)
    if st=='block': footer='/*\n'+''.join(' * '+line+'\n' for line in lines)+' */\n'
    elif st=='REM': footer='\n'.join('REM '+line for line in lines)+'\n'
    else: footer='\n'.join(st+' '+line for line in lines)+'\n'
    original=remove_legacy_generic_output(original,path)
    return original.rstrip()+'\n\n'+footer, ('salida_documentada' if client is not None and client.relative_to(pack).as_posix() in cache or source_out and not source_out[0].startswith('(salida del programa por verificar') else ('sin_entrada' if not exe else 'pendiente'))


def valid_complete_guide(src: str, path: Path) -> bool:
    """Accept independently authored, complete guides in --check.

    --write keeps generating canonical footers. --check verifies the actual
    instructions rather than forcing identical template punctuation.
    """
    if BEGIN not in src or END not in src:
        return False
    try:
        stripped = strip_generated(src, path)
    except ValueError:
        return False
    if stripped == src:
        return False
    footer = src[src.rfind(BEGIN):src.rfind(END)]
    # A complete Java exercise may name its java21 working folder
    # explicitly instead of the generic "carpeta del lenguaje".
    locations = (
        'Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):',
        'Ejemplo en consola (PowerShell; desde la carpeta java21):',
    )
    if not any(location in footer for location in locations):
        return False
    if 'Salida esperada:' not in footer:
        return False
    if GENERIC.search(footer):
        return False
    if path.suffix == '.java' and ('javac --release 21' not in footer
                                   or 'java -cp $out' not in footer):
        return False
    return True


def main():
    ap=argparse.ArgumentParser()
    mx=ap.add_mutually_exclusive_group(required=True)
    mx.add_argument('--write',action='store_true');mx.add_argument('--check',action='store_true')
    ap.add_argument('--root',type=Path,default=Path('src/main/resources/practice'))
    ap.add_argument('--outcomes',type=Path,default=None)
    args=ap.parse_args();root=args.root.resolve()
    if not root.is_dir(): raise SystemExit(f'No existe carpeta de biblioteca: {root}')
    sources={p:p.read_text(encoding='utf-8') for p in root.rglob('*') if p.is_file() and p.suffix in EXT}
    expected=sum(1 for p,s in sources.items() if MARKERS.search(strip_generated(s,p)))
    cache={}
    if args.outcomes:
        observations=json.loads(args.outcomes.read_text(encoding='utf-8'))
        if isinstance(observations,list): cache={v['path']:v['stdout'].rstrip('\n') for v in observations if v.get('status')=='ok'}
        elif isinstance(observations,dict): cache=observations
    stats=Counter();changes=[];incomplete=[]
    for p,src in sources.items():
        new,state=render(p,src,root,sources,cache)
        stats[state]+=1
        if new!=src:
            # Exact generated wording is not required when the author supplied
            # a complete non-generic, executable and correctly delimited guide.
            if not (args.check and valid_complete_guide(src, p)):
                changes.append(p)
                if args.write: p.write_text(new,encoding='utf-8',newline='\n')
        if state=='pendiente': incomplete.append(str(p.relative_to(root)))
    print(f'CodeTrace: archivos={len(sources)}; bloques originales={expected}; cambios={len(changes)}; estados={dict(stats)}')
    if incomplete: print('Salidas pendientes de verificar:\n'+'\n'.join(incomplete[:120]))
    if args.check and changes: raise SystemExit(f'FALTAN {len(changes)} archivos con guia de consola.')
    # Deliberadamente no se considera éxito una salida textual genérica: se informa.
    if args.check and incomplete: raise SystemExit(f'Salidas concretas pendientes: {len(incomplete)}')

if __name__=='__main__': main()
