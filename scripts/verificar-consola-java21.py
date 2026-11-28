"""Control editorial y comprobación de los comandos directos de Java 21."""
import argparse
import concurrent.futures
import json
import pathlib
import re
import shlex
import shutil
import subprocess
import tempfile

repo = pathlib.Path(__file__).resolve().parent.parent
root = repo / 'src/main/resources/practice/java21'
base = 'c6362835720b02cab83493940f55127cdb9b66e6'
paths = json.loads((repo / 'scripts/java-consola-rutas.json').read_text(encoding='utf-8'))
assert len(paths) == len(set(paths)) == 244

# Literales (incluidos bloques de texto) preceden a los comentarios, de modo
# que // dentro de una cadena nunca se elimina al comparar.
lexer = re.compile(r'(?P<literal>"""(?:\\.|(?!""")[\s\S])*?"""|"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\')|(?P<comment>//[^\r\n]*|/\*[\s\S]*?\*/)|(?P<token>\w+|[^\s])')
def tokens(source):
    return [m.group() for m in lexer.finditer(source) if m.lastgroup != 'comment']

changed = subprocess.check_output(['git','diff','--name-only',base,'HEAD','--','src/main/resources/practice/java21/0[0-5]-*'],cwd=repo,text=True,encoding='utf-8').splitlines()
assert set(changed)==set(paths), (set(changed)-set(paths),set(paths)-set(changed))
commands=set()
for rel in paths:
    p=repo/rel
    src=p.read_text(encoding='utf-8')
    original=subprocess.check_output(['git','show',base+':'+rel],cwd=repo).decode('utf-8')
    assert tokens(src)==tokens(original), ('Cambio no editorial',rel)
    assert not re.search(r'PowerShell|Get-ChildItem|\$env:TEMP|\$out\b',src),rel
    cmd=re.search(r'^// Compilar: (javac .+)$',src,re.M)
    assert cmd,rel
    cwd=root/'05-patrones-diseno-counter-strike' if '// Desde la raíz de 05-patrones-diseno-counter-strike.' in src else p.parent
    commands.add((cwd,cmd.group(1)))

print(f'Preservación aprobada: {len(paths)} archivos; {len(commands)} comandos distintos')
ap=argparse.ArgumentParser()
ap.add_argument('--solo-preservacion',action='store_true')
if not ap.parse_args().solo_preservacion:
    def check(item):
        cwd,command=item
        with tempfile.TemporaryDirectory() as tmp:
            target=pathlib.Path(tmp)
            shutil.copytree(cwd,target,dirs_exist_ok=True)
            argv=shlex.split(command)
            args=[]
            for arg in argv:
                if '*' in arg:
                    expanded=sorted(p.relative_to(target).as_posix() for p in target.glob(arg))
                    assert expanded,(cwd,arg)
                    args+=expanded
                else:
                    args.append(arg)
            r=subprocess.run(args,cwd=target,capture_output=True,text=True,encoding='utf-8',timeout=60)
            assert r.returncode==0,(cwd,command,r.stderr)
            return str(cwd.relative_to(root))
    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as pool:
        for name in pool.map(check,sorted(commands,key=lambda x:(str(x[0]),x[1]))):print('OK',name)
    print(f'{len(commands)} comandos aprobados')
