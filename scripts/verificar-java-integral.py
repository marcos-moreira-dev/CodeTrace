"""Compile and run each declared independent Java integral exercise."""
import json
import os
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
        classpath = tmp
        def checked(command):
            result = subprocess.run(command, cwd=tmp, timeout=30,
                                    capture_output=True, text=True, encoding="utf-8")
            assert result.returncode == 0, (case["path"], command[0], result.stderr)
        if "jar" in case:
            # Verify the real library boundary: compile separately, package,
            # then compile and run the client using the JAR, not loose classes.
            spec = case["jar"]
            assert set(spec["library"] + spec["client"]) == set(case["sources"])
            lib = Path(tmp) / "lib"
            app = Path(tmp) / "app"
            jar = Path(tmp) / "biblioteca.jar"
            lib.mkdir()
            app.mkdir()
            checked(["javac", "--release", "21", "-encoding", "UTF-8", "-d", str(lib),
                     *[str(root / p) for p in spec["library"]]])
            checked(["jar", "--create", "--file", str(jar), "-C", str(lib), "."])
            checked(["javac", "--release", "21", "-encoding", "UTF-8", "-cp", str(jar),
                     "-d", str(app), *[str(root / p) for p in spec["client"]]])
            classpath = os.pathsep.join([str(jar), str(app)])
        else:
            checked(["javac", "--release", "21", "-encoding", "UTF-8", "-d", tmp,
                     *map(str, sources)])
        command = ["java", "-Dfile.encoding=UTF-8", "-cp", classpath, case["main"]]
        if case.get("executable_jar"):
            jar = Path(tmp).parent / (Path(tmp).name + ".jar")
            # Store the artifact outside the class directory to avoid archiving itself.
            try:
                checked(["jar", "--create", "--file", str(jar), "--main-class", case["main"], "-C", tmp, "."])
                checked(["java", "-Dfile.encoding=UTF-8", "-jar", str(jar)])
            finally:
                jar.unlink(missing_ok=True)
        run = subprocess.run([*command,
                              *case.get("args", [])], cwd=tmp, timeout=20, input=case.get("stdin", ""),
                             capture_output=True, text=True, encoding="utf-8")
        assert run.returncode == 0 and run.stderr == case.get("stderr", "") and run.stdout in case.get("allowed_stdout", [case["stdout"]]), (
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
