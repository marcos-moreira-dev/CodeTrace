"""Verificador aislado; los comandos de práctica se documentan en las fuentes."""
import json, pathlib, shutil, subprocess, tempfile, re, sys, concurrent.futures
root=pathlib.Path(__file__).resolve().parent.parent
base=root/'src/main/resources/practice/typescript/00-fundamentos-del-lenguaje'
cases=json.loads((root/'scripts/typescript-fundamentos-casos.json').read_text(encoding='utf-8'))
tsc=shutil.which('tsc')
assert tsc, 'tsc no disponible'
def run(cmd,cwd):
    if sys.platform=='win32' and cmd[0].lower().endswith('.cmd'):
        cmd=['cmd','/d','/c',*cmd]
    return subprocess.run(cmd,cwd=cwd,capture_output=True,text=True,encoding='utf-8',timeout=30)
def check(c):
    p=base/c['path']
    assert p.is_file(),p
    s=p.read_text(encoding='utf-8')
    assert '// Tema:' in s and '// Compilar:' in s,p
    with tempfile.TemporaryDirectory() as tmp:
        d=pathlib.Path(tmp)
        if c['project']:
            shutil.copytree(p.parent,d,dirs_exist_ok=True)
            cmd=[tsc,'-p','.']
        else:
            shutil.copy2(p,d/p.name)
            for x in c.get('sources',[]):shutil.copy2(p.parent/x,d/x)
            cmd=[tsc,'--strict','--target','ES2022',*c.get('flags',[])]
            if c['mode']!='R':cmd+=['--noEmit']
            else:cmd+=['--noEmitOnError']
            cmd+=[p.name]
        result=run(cmd,d)
        out=result.stdout+result.stderr
        if c['mode']=='N':
            found=sorted(int(n) for n in re.findall(r'error TS(\d+):',out))
            assert result.returncode!=0 and found==sorted(c['codes']),(p,out)
        else:
            assert result.returncode==0,(p,out)
            if c['mode']=='R':
                result=run(['node',p.stem+'.js'],d)
                assert result.returncode==0 and not result.stderr and result.stdout==c['stdout'],(p,result.stdout,result.stderr)
    return c['path']
with concurrent.futures.ThreadPoolExecutor(max_workers=4) as pool:
    for path in pool.map(check,cases): print('OK',path)
print(f'{len(cases)} casos aprobados')
