"""Verifica ejemplos independientes de la JLS 21; no altera la biblioteca."""
import json
import subprocess
import tempfile
from pathlib import Path

repo = Path(__file__).resolve().parent.parent
root = repo / "src/main/resources/practice/java21/00-fundamentos-especificacion-oficial"
cases = json.loads((repo / "scripts/jls21-casos.json").read_text(encoding="utf-8"))
covered = set()
for case in cases:
    source = root / case["path"]
    unit = root / case["unit"] if "unit" in case else source.parent
    sources = sorted(unit.rglob("*.java"))
    assert source in sources and sources, case["path"]
    for item in sources:
        text = item.read_text(encoding="utf-8")
        assert "Referencia: Java SE 21, JLS" in text and "Compilar:" in text, item
        assert "Salida esperada:" in text, item
        if "reject" in case:
            assert "ERROR INTENCIONAL" in text, item
        else:
            assert "Ejecutar:" in text, item
    covered.update(s.relative_to(root).as_posix() for s in sources)
    with tempfile.TemporaryDirectory() as tmp:
        if "stages" in case:
            seen = set()
            for stage in case["stages"]:
                stage_sources = [unit / name for name in stage["sources"]]
                seen.update(stage_sources)
                comp = subprocess.run(["javac", "-J-Duser.language=en", "-J-Duser.country=US",
                                "--release", "21", "-encoding", "UTF-8",
                                "-cp", tmp, "-d", tmp, *map(str, stage_sources)],
                                capture_output=True, text=True, encoding="utf-8", timeout=30)
                if "reject" in stage:
                    assert comp.returncode != 0 and stage["reject"].lower() in comp.stderr.lower(), (case["path"], comp.stderr)
                    continue
                assert comp.returncode == 0, (case["path"], comp.stderr)
                run = subprocess.run(["java", "-Dfile.encoding=UTF-8", "-cp", tmp, case["main"]],
                                     capture_output=True, text=True, encoding="utf-8", timeout=20)
                assert run.stdout == stage["stdout"], (case["path"], run.stderr, run.stdout)
                if "exception" in stage:
                    assert run.returncode != 0 and stage["exception"] in run.stderr, (case["path"], run.stderr)
                else:
                    assert run.returncode == 0 and not run.stderr, (case["path"], run.stderr)
            assert seen == set(sources), case["path"]
            print("OK etapas binarias", case["section"], case["path"])
            continue
        if "module" in case:
            subprocess.run(["javac", "--release", "21", "-encoding", "UTF-8", "--module-source-path", str(unit),
                            "-d", tmp, *map(str, sources)], check=True, timeout=30)
            run = subprocess.run(["java", "--module-path", tmp, "-m", case["module"]],
                                 capture_output=True, text=True, encoding="utf-8", timeout=20)
            assert run.returncode == 0 and run.stdout == case["stdout"] and not run.stderr, (case["path"], run.stderr, run.stdout)
            print("OK módulo", case["section"], case["path"])
            continue
        compilation = subprocess.run(["javac", "-J-Duser.language=en", "-J-Duser.country=US",
                        "--release", "21", "-encoding", "UTF-8",
                        "-d", tmp, *map(str, sources)], capture_output=True,
                        text=True, encoding="utf-8", timeout=30)
        if "reject" in case:
            assert compilation.returncode != 0 and case["reject"] in compilation.stderr, (
                case["path"], compilation.stderr)
            print("OK rechazo esperado", case["section"], case["path"])
            continue
        assert compilation.returncode == 0, (case["path"], compilation.stderr)
        result = subprocess.run(["java", "-Dfile.encoding=UTF-8", *case.get("jvm_args", []), "-cp", tmp,
                                 case["main"], *case["args"]], capture_output=True,
                                text=True, encoding="utf-8", timeout=20)
        allowed = case.get("allowed_stdout", [case["stdout"]])
        assert result.stdout in allowed, (case["path"], repr(result.stdout), allowed)
        if "exception" in case:
            assert result.returncode != 0 and case["exception"] in result.stderr, case["path"]
        else:
            assert result.returncode == 0 and not result.stderr, (case["path"], result.stderr)
        print("OK", case["section"], case["path"])
assert covered == {p.relative_to(root).as_posix() for p in root.rglob("*.java")}
print(f"JLS 21: {len(cases)} casos verificados")
