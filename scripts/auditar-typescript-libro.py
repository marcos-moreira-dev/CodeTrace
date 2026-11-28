"""Diagnóstico inicial de los bloques originales; no ejecuta el código."""
import concurrent.futures
import json
import pathlib
import subprocess
import tempfile
from collections import Counter

repo = pathlib.Path(__file__).resolve().parent.parent
root = repo / "curriculum/typescript-book"
manifest = json.loads((root / "inventario.json").read_text(encoding="utf-8"))
units = [u for u in manifest["units"] if u["language"] == "typescript"]
assert len(units) == 266
assert len({u["path"] for u in units}) == len(units)
assert {p.relative_to(root).as_posix() for p in (root / "01-fundamentos").rglob("*.ts")} == {u["path"] for u in units}

def check(unit):
    source = root / unit["path"]
    text = source.read_text(encoding="utf-8")
    assert text.startswith("// Tema:\n"), source
    assert len(text.splitlines()) == unit["source_lines"] + unit["added_header_lines"], source
    with tempfile.TemporaryDirectory() as tmp:
        result = subprocess.run(["tsc", "--noEmit", "--strict", "--target", "ES2022",
                                 "--module", "nodenext", "--lib", "esnext,dom", str(source)],
                                cwd=tmp, capture_output=True, text=True, timeout=45)
    return {"path": unit["path"], "section": unit["section"],
            "returncode": result.returncode,
            "diagnostics": result.stdout + result.stderr,
            "classification": "sin_diagnosticos" if result.returncode == 0 else "requiere_revision"}

subprocess.run(["tsc", "--version"], check=True)
with concurrent.futures.ThreadPoolExecutor(max_workers=4) as pool:
    results = list(pool.map(check, units))
counts = Counter(r["classification"] for r in results)
print(dict(counts))
pathlib.Path("typescript-book-audit.json").write_text(json.dumps(results, ensure_ascii=False, indent=2), encoding="utf-8")
print("Informe generado. Un diagnóstico no implica que un ejemplo sea incorrecto: puede ser intencional o necesitar contexto.")
