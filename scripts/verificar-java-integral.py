"""Compile and run each declared independent Java integral exercise."""
import csv
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

# The agreed inventory is the source of coverage, including documentary units.
with (repo / "docs/JAVA-INTEGRAL-INVENTARIO.tsv").open(encoding="utf-8", newline="") as f:
    inventory = list(csv.DictReader(f, delimiter="\t"))
planned = ["/".join((row["area"], row["unidad"], row["archivo"])) for row in inventory]
assert len(planned) == len(set(planned)) == 525
assert all(row["estado"] in {"implementado_pendiente_ci", "verificado_ci"} for row in inventory)
planned_java = {path for row, path in zip(inventory, planned) if row["modalidad"] != "D"}
planned_docs = set(planned) - planned_java
assert len(planned_java) == 523 and len(planned_docs) == 2
assert len({(row["area"], row["unidad"]) for row in inventory}) == 214
assert actual == planned_java, (actual - planned_java, planned_java - actual)
assert {p.relative_to(root).as_posix() for a in areas for p in a.rglob("*") if p.is_file()} == set(planned)
catalog = set((root.parent / "catalog.tsv").read_text(encoding="utf-8").splitlines())
assert all("java21\t" + path in catalog for path in planned)
assert all((root / path).read_text(encoding="utf-8").strip() for path in planned_docs)
print("Inventario completo: 523 fuentes, 2 documentos, 214 unidades")

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
        if case["path"].startswith("20-arquitectura-de-software/"):
            verify_bytecode_boundaries(case, Path(tmp), checked)
        command = ["java", "-Dfile.encoding=UTF-8", "-cp", classpath, case["main"]]
        if case.get("executable_jar"):
            # Compile into a dedicated directory; package it and validate the
            # actual executable JAR's stdout using the same assertions below.
            classes = Path(tmp) / "classes"
            classes.mkdir()
            checked(["javac", "--release", "21", "-encoding", "UTF-8", "-d", str(classes),
                     *map(str, sources)])
            jar = Path(tmp) / "aplicacion.jar"
            checked(["jar", "--create", "--file", str(jar), "--main-class", case["main"], "-C", str(classes), "."])
            command = ["java", "-Dfile.encoding=UTF-8", "-jar", str(jar)]
        run = subprocess.run([*command,
                              *case.get("args", [])], cwd=tmp, timeout=20, input=case.get("stdin", ""),
                             capture_output=True, text=True, encoding="utf-8")
        assert run.returncode == 0 and run.stderr == case.get("stderr", "") and run.stdout in case.get("allowed_stdout", [case["stdout"]]), (
            case["path"], run.returncode, run.stdout, run.stderr, case["stdout"])
    return case["path"]

# Check architecture boundaries using local package imports. Each unit is
# compiled independently; standard-library imports are outside this graph.
import re
from collections import defaultdict

architecture = root / "20-arquitectura-de-software"
rules = {
    "01-en-capas-verduleria": {"dominio": set(), "persistencia": {"dominio"}, "servicio": {"dominio", "persistencia"}, "presentacion": {"servicio", "dominio"}},
    "02-mvc-biblioteca": {"modelo": set(), "vista": {"modelo"}, "controlador": {"modelo", "vista"}},
    "03-mvp-panaderia": {"modelo": set(), "vista": set(), "presentador": {"modelo", "vista"}},
    "04-mvvm-heladeria": {"modelo": set(), "viewmodel": {"modelo"}, "vista": {"viewmodel"}},
    "05-hexagonal-centro-ecuestre": {"dominio": set(), "aplicacion": {"dominio", "aplicacion.puertos"}, "aplicacion.puertos": {"dominio"}, "infraestructura.entrada": {"aplicacion.puertos", "dominio"}, "infraestructura.salida": {"aplicacion.puertos", "dominio"}},
    "06-clean-peluqueria-canina": {"entidades": set(), "casosdeuso": {"entidades"}, "adaptadores": {"casosdeuso"}, "infraestructura": {"casosdeuso", "entidades"}},
    "07-onion-cabanas": {"dominio": set(), "aplicacion": {"dominio"}, "infraestructura": {"dominio"}, "presentacion": {"aplicacion"}},
    "08-eventos-restaurante": {"dominio": set(), "eventos": set(), "cocina": {"dominio", "eventos"}, "mostrador": {"eventos"}},
    "09-monolito-modular-supermercado": {"inventario": set(), "compras": {"inventario"}, "ventas": {"inventario"}},
    "10-microservicios-bicicletas": {"disponibilidad": set(), "reservas": set(), "cliente": set()},
    "11-comparacion-verduleria/02-hexagonal": {"dominio": set(), "aplicacion": {"dominio", "aplicacion.puertos"}, "aplicacion.puertos": {"dominio"}, "infraestructura": {"aplicacion.puertos", "dominio"}},
    "11-comparacion-verduleria/03-clean": {"entidades": set(), "casosdeuso": {"entidades"}, "adaptadores": {"casosdeuso"}, "infraestructura": {"casosdeuso", "entidades"}},
}
def verify_bytecode_boundaries(case, compiled, checked):
    # Unlike import inspection, jdeps sees compiled references even if Java
    # uses fully qualified names. Match only classes belonging to this unit.
    folder = case["path"].removeprefix("20-arquitectura-de-software/").rsplit("/", 1)[0]
    allowed = rules[folder]
    classes = {".".join(p.relative_to(compiled).with_suffix("").parts)
               for p in compiled.rglob("*.class")}
    command = ["jdeps", "-verbose:class", "-filter:none", "-cp", str(compiled), str(compiled)]
    result = subprocess.run(command, cwd=compiled, timeout=30, capture_output=True,
                            text=True, encoding="utf-8")
    assert result.returncode == 0, (case["path"], result.stderr)
    checked_classes = set()
    for line in result.stdout.splitlines():
        match = re.match(r"^\s+(\S+)\s+->\s+(\S+)\s+(.+)$", line)
        if not match:
            continue
        source, target, location = match.groups()
        if source not in classes:
            continue
        checked_classes.add(source)
        assert location.strip() != "not found", (case["path"], source, target)
        if target not in classes:
            continue  # standard library
        origin = source.rpartition(".")[0]
        destination = target.rpartition(".")[0]
        if not origin or origin == destination:
            continue  # composition root or intra-package detail
        assert origin in allowed and destination in allowed[origin], (case["path"], source, target)
    assert checked_classes, ("jdeps returned no classes", case["path"])

for folder, allowed in rules.items():
    base = architecture / folder
    graph = defaultdict(set)
    for source in base.rglob("*.java"):
        package = ".".join(source.relative_to(base).parts[:-1])
        content = source.read_text(encoding="utf-8")
        if not package:
            assert source.name == "Main.java", source
            continue  # composition root is allowed to wire all layers
        assert package in allowed, source
        assert re.search(r"^package " + re.escape(package) + r";", content, re.M), source
        for imp in re.findall(r"^import\s+(?:static\s+)?([\w.]+(?:\*)?)\s*;", content, re.M):
            target = imp.rsplit(".", 1)[0]
            if target == package or imp.startswith(("java.", "javax.", "com.sun.net.httpserver.")):
                continue
            assert target in allowed[package], (source, imp)
            graph[package].add(target)
    def visit(node, stack):
        assert node not in stack, (folder, node, stack)
        for target in graph[node]:
            visit(target, stack | {node})
    for node in list(graph):
        visit(node, set())

comparison_paths = [
    "20-arquitectura-de-software/01-en-capas-verduleria/Main.java",
    "20-arquitectura-de-software/11-comparacion-verduleria/02-hexagonal/Main.java",
    "20-arquitectura-de-software/11-comparacion-verduleria/03-clean/Main.java",
]
outputs = {case["stdout"] for case in cases if case["path"] in comparison_paths}
assert len(outputs) == 1 and sum(case["path"] in comparison_paths for case in cases) == 3
print("Arquitecturas: limites de imports y escenario comparable comprobados")

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
