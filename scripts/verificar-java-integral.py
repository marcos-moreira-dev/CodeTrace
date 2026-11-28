"""Compile and run each declared independent Java integral exercise."""
import json
import subprocess
import tempfile
from pathlib import Path
from concurrent.futures import ThreadPoolExecutor, as_completed

repo = Path(__file__).resolve().parent.parent
root = repo / "src/main/resources/practice/java21"
cases = json.loads((repo / "scripts/java-integral-casos.json").read_text(encoding="utf-8"))
areas = {p for p in root.iterdir() if p.is_dir() and p.name.split("-")[0].isdigit()
         and 6 <= int(p.name.split("-")[0]) <= 20}
actual = {p.relative_to(root).as_posix() for a in areas for p in a.rglob("*.java")}
declared = {p for c in cases for p in c["sources"]}
assert actual == declared, (actual - declared, declared - actual)
assert len({c["path"] for c in cases}) == len(cases)

def verify(case):
    assert case["path"] in case["sources"], case
    sources = [root / p for p in case["sources"]]
    for p in sources:
        s = p.read_text(encoding="utf-8")
        assert s.startswith("// Tema:\n") and "Compilar:" in s and "Salida esperada:" in s, p
    with tempfile.TemporaryDirectory() as tmp:
        compilation = subprocess.run(["javac", "--release", "21", "-encoding", "UTF-8", "-d", tmp,
                        *map(str, sources)], timeout=30, capture_output=True, text=True, encoding="utf-8")
        assert compilation.returncode == 0, (case["path"], compilation.stderr)
        run = subprocess.run(["java", "-Dfile.encoding=UTF-8", "-cp", tmp, case["main"],
                              *case.get("args", [])], cwd=tmp, timeout=20,
                             capture_output=True, text=True, encoding="utf-8")
        assert run.returncode == 0 and not run.stderr and run.stdout in case.get("allowed_stdout", [case["stdout"]]), (
            case["path"], run.returncode, run.stdout, run.stderr, case["stdout"])
    return case["path"]

failures = []
with ThreadPoolExecutor(max_workers=4) as pool:
    futures = {pool.submit(verify, c): c["path"] for c in cases}
    for future in as_completed(futures):
        try:
            print("OK", future.result())
        except Exception as e:
            failures.append((futures[future], str(e)))
            print("FALLO", futures[future], repr(e))
if failures:
    raise SystemExit(f"{len(failures)} de {len(cases)} casos fallaron")
print(f"Java integral: {len(cases)} casos, {len(actual)} fuentes verificadas")
