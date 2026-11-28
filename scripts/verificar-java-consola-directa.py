"""Verificación interna: preservar código y compilar las guías de consola Java."""
import argparse, concurrent.futures, pathlib, re, shlex, shutil, subprocess, tempfile
repo=pathlib.Path(__file__).resolve().parent.parent
root=repo/'src/main/resources/practice/java21'
prefix='src/main/resources/practice/java21/'
# Los comentarios se ignoran, pero las cadenas, caracteres y bloques de texto se mantienen íntegros.
lex=re.compile(r'"""(?:\\.|(?!""")[\s\S])*?"""|"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|//[^\r\n]*|/\*[\s\S]*?\*/|\s+|.',re.S)
def tokens(s):
    return [t for t in lex.findall(s) if not t.isspace() and not t.startswith(('//','/*'))]
def git(*args):
    return subprocess.check_output(['git',*args],cwd=repo).decode('utf-8')
def baseline(ref):
    diff=git('diff','--name-status',ref,'HEAD','--',prefix+'0[0-5]-*')
    changed=diff.splitlines()
    assert len(changed)==244, len(changed)
    for line in changed:
        kind,path=line.split('\t')
        assert kind=='M' and path.endswith('.java') and not path.startswith(prefix+'00-'), line
        before=git('show',ref+':'+path)
        after=(repo/path).read_text(encoding='utf-8')
        assert tokens(before)==tokens(after),path
    print('244 archivos: tokens de código preservados')
def groups():
    result={}
    paths=sorted(root.rglob('*.java'))
    assert len(paths)==1327,len(paths)
    direct=0
    for p in paths:
        s=p.read_text(encoding='utf-8')
        assert not re.search(r'PowerShell|Get-ChildItem|Join-Path|\$env:TEMP|\$out',s),p
        if not s.startswith('// Compilar (desde'):continue
        direct+=1
        first=s.splitlines()[0]
        cmd=first.split('): ',1)[1]
        assert cmd.startswith('javac ') and 'Salida esperada:' in s,p
        folder=root/'05-patrones-diseno-counter-strike' if 'desde la raíz de 05-' in first else p.parent
        result[(folder,cmd)]=True
    assert direct==244,direct
    assert len(result)==84,len(result)
    return list(result)
def compile_one(group):
    folder,cmd=group
    with tempfile.TemporaryDirectory() as tmp:
        d=pathlib.Path(tmp)
        shutil.copytree(folder,d,dirs_exist_ok=True)
        args=[]
        for a in shlex.split(cmd):
            if '*' in a:
                matches=sorted(x.relative_to(d).as_posix() for x in d.glob(a))
                assert matches,(folder,a)
                args.extend(matches)
            else:args.append(a)
        run=subprocess.run(args,cwd=d,capture_output=True,text=True,encoding='utf-8',timeout=60)
        assert run.returncode==0,(folder,run.stdout,run.stderr)
    return folder.relative_to(root).as_posix()
ap=argparse.ArgumentParser()
ap.add_argument('--baseline')
ap.add_argument('--compile',action='store_true')
a=ap.parse_args()
g=groups()
if a.baseline:baseline(a.baseline)
if a.compile:
    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as pool:
        for name in pool.map(compile_one,g):print('OK',name)
    print('84/84 grupos de compilación directa')
