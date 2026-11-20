#!/usr/bin/env bash
# CodeTrace | Biblioteca v2: prueba real de clientes multiarchivo.
# Ejecutar en Ubuntu/WSL tras instalar JDK 21, Python, Node, TypeScript,
# GCC/G++, Go, Ruby, POSIX sh y NASM. .NET 8 es opcional.
set -euo pipefail
cd "$(dirname "$0")/.."
base="src/main/resources/practice"
tmp="$(mktemp -d)"
trap 'rm -rf "$tmp"' EXIT

echo "Validación de 14 paquetes — recursos offline"
python3 - "$base" <<'PY'
from pathlib import Path
import sys
base = Path(sys.argv[1])
catalog = (base / "catalog.tsv").read_text(encoding="utf-8")
items = [line.split("\t", 1) for line in catalog.splitlines() if line and not line.startswith("#")]
assert len(items) == 226, len(items)
assert len({x[0] for x in items}) == 14
for pack, path in items:
    source = base / pack / path
    assert source.is_file(), source
    lines = source.read_text(encoding="utf-8").splitlines()[:10]
    assert any("CodeTrace" in x for x in lines), source
    assert any("Referencia:" in x for x in lines), source
    assert any("Compilar" in x or "Comprobar" in x or "Ejecutar" in x for x in lines), source
print("Catálogo y metadatos:", len(items))
PY

python3 -B "$base/python/03-algoritmos/programacion-dinamica/main.py"
python3 -B "$base/python/02-estructuras-datos/cola-circular/main.py"

mkdir -p "$tmp/java"
find "$base/java21" -name '*.java' -print0 | xargs -0 javac --release 21 -encoding UTF-8 -d "$tmp/java"
java -cp "$tmp/java" codetrace.practica.monedas.MainMonedas
java -cp "$tmp/java" codetrace.practica.heaps.MainColaPrioridad
java -cp "$tmp/java" codetrace.practica.tarifas.MainTarifas

gcc -std=c17 -Wall -Wextra "$base/c/02-estructuras-datos/pila/main.c" \
    "$base/c/02-estructuras-datos/pila/pila.c" -o "$tmp/pila"
"$tmp/pila"
gcc -std=c17 -Wall -Wextra "$base/c/04-patrones-diseno/bibliotecas/main.c" \
    "$base/c/04-patrones-diseno/bibliotecas/operaciones.c" -o "$tmp/biblioteca"
"$tmp/biblioteca"

g++ -std=c++20 -Wall -Wextra "$base/cpp/04-patrones-diseno/strategy/main.cpp" \
    "$base/cpp/04-patrones-diseno/strategy/estrategias.cpp" -o "$tmp/strategy-cpp"
"$tmp/strategy-cpp"

tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" \
    "$base/typescript/03-algoritmos/programacion-dinamica/Principal.ts"
node "$tmp/ts/Principal.js"
node "$base/javascript/03-algoritmos/cuatro_reinas.js"
go run "$base/go/01-fundamentos/goroutines_canales.go"
ruby -c "$base/ruby/01-fundamentos/bloques_y_enumerables.rb"
sh -n "$base/shell/01-fundamentos/variables_y_quoting.sh"
sh "$base/shell/03-algoritmos/frecuencia_de_palabras.sh"

nasm -f elf64 "$base/nasm-x64/03-algoritmos/busqueda_lineal.asm" -o "$tmp/busqueda.o"
ld "$tmp/busqueda.o" -o "$tmp/busqueda"
"$tmp/busqueda"
echo "OK: clientes, catálogo y lenguajes comprobados"
