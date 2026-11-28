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
    sources = sorted(source.parent.rglob("*.java"))
    covered.update(s.relative_to(root).as_posix() for s in sources)
    with tempfile.TemporaryDirectory() as tmp:
        subprocess.run(["javac", "--release", "21", "-encoding", "UTF-8",
                        "-d", tmp, *map(str, sources)], check=True, timeout=30)
        result = subprocess.run(["java", "-Dfile.encoding=UTF-8", "-cp", tmp,
                                 case["main"], *case["args"]], capture_output=True,
                                text=True, encoding="utf-8", timeout=20)
        assert result.stdout == case["stdout"], (case["path"], repr(result.stdout))
        if "exception" in case:
            assert result.returncode != 0 and case["exception"] in result.stderr, case["path"]
        else:
            assert result.returncode == 0 and not result.stderr, (case["path"], result.stderr)
        print("OK", case["section"], case["path"])
assert covered == {p.relative_to(root).as_posix() for p in root.rglob("*.java")}
print(f"JLS 21: {len(cases)} casos verificados")
