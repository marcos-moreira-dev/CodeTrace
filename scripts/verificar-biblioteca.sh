#!/usr/bin/env bash
# CodeTrace | Referencia: biblioteca de práctica v5 / verificador GNU/Linux-WSL
# Ejecutar en COPIA: bash scripts/verificar-biblioteca.sh
# Artefactos de compilación aislados en mktemp, sin tocar referencias.
set -euo pipefail
cd "$(dirname "$0")/.."
base=src/main/resources/practice
tmp=$(mktemp -d)
trap 'rm -rf "$tmp"' EXIT
mkdir -p "$tmp/java" "$tmp/ts"

python3 - "$base" <<'PY'
from pathlib import Path
import sys
root=Path(sys.argv[1])
items=[x.split("\t",1) for x in (root/"catalog.tsv").read_text(encoding="utf-8").splitlines()
       if x and not x.startswith("#")]
assert len(items)==466, len(items)
assert len({p for p,_ in items})==14
assert len(set(map(tuple,items)))==len(items)
assert all(len(path.split("/"))>=3 for _,path in items)
actual={str(x.relative_to(root)).replace("\\","/") for x in root.rglob("*")
        if x.is_file() and x.name!="catalog.tsv"}
declared={p+"/"+path for p,path in items}
assert actual==declared, (actual-declared,declared-actual)
for pack,path in items:
    lines=(root/pack/path).read_text(encoding="utf-8").splitlines()[:12]
    head="\n".join(lines).lower()
    assert "codetrace" in head, (pack,path)
    assert "referencia" in head or "kotlin 2.2" in head, (pack,path)
    assert any(word in head for word in ("compilar","comprobar","ejecutar","verificar","tipos/sintaxis")), (pack,path)
print("OK catálogo:",len(items))
PY

echo "Java 21: clases, contratos y clientes"
find "$base/java21" -name '*.java' -print0 | xargs -0 javac --release 21 -encoding UTF-8 -d "$tmp/java"
for cls in \
 MainExcepcionChecked MainExcepcionUnchecked MainRecursosYSuppressed \
 MainColaCircular MainListaSimple MainPila MainBusquedaBinaria \
 codetrace.factorymethod.MainFactoryMethod codetrace.observer.MainObserver MainStrategy \
 codetrace.practica.heaps.MainColaPrioridad \
 codetrace.practica.grafostipos.MainTiposGrafo \
 codetrace.practica.grafosrepresentaciones.MainRepresentaciones \
 codetrace.practica.grafoscaminos.MainCaminosMinimos \
 codetrace.practica.grafosmst.MainExpansionMinima \
 codetrace.practica.grafostopologico.MainTopologico \
 codetrace.practica.grafosscc.MainComponentesFuertes; do
  java -cp "$tmp/java" "$cls" >/dev/null
done

echo "C17: pila .h/.c y bibliotecas .a/.so"
gcc -std=c17 -Wall -Wextra -Werror \
 "$base/c/02-estructuras-datos/pila/main.c" \
 "$base/c/02-estructuras-datos/pila/pila.c" -o "$tmp/pila-c"
"$tmp/pila-c" >/dev/null
sh "$base/c/01-fundamentos/compilacion-y-enlazado/biblioteca-estatica-dinamica/compilar.sh" >/dev/null

echo "C++20: .hpp/.cpp, RAII, herencia y bibliotecas"
sh "$base/cpp/01-fundamentos/compilacion-y-enlazado/biblioteca-estatica-dinamica/compilar.sh" >/dev/null
for unit in \
 02-estructuras-datos/arbol-bst \
 02-estructuras-datos/pila \
 04-patrones-diseno/factory-method/exportadores \
 04-patrones-diseno/adapter/unidades \
 04-patrones-diseno/decorator/notificaciones \
 04-patrones-diseno/strategy; do
  g++ -std=c++20 -Wall -Wextra -Werror "$base/cpp/$unit/"*.cpp -o "$tmp/cpp"
  "$tmp/cpp" >/dev/null
done

echo "TypeScript: módulos y clientes separados"
for unit in \
 02-estructuras-datos/pila/MainPila.ts \
 02-estructuras-datos/lista-simple/MainListaSimple.ts \
 02-estructuras-datos/arbol-bst/MainArbolBST.ts \
 02-estructuras-datos/heap/MainHeap.ts \
  04-patrones-diseno/factory/basico/MainFactory.ts \
 04-patrones-diseno/factory-method/documentos/MainFactoryMethod.ts \
 04-patrones-diseno/strategy/basico/MainStrategy.ts \
 04-patrones-diseno/adapter/unidades/MainAdapter.ts \
 04-patrones-diseno/decorator/notificaciones/MainDecorator.ts \
 04-patrones-diseno/observer/basico/MainObserverBasico.ts \
 03-algoritmos/programacion-dinamica/MainMonedas.ts; do
   tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/$unit"
   js=$(basename "$unit" .ts)
   node "$tmp/ts/$js.js" >/dev/null
done

echo "Python: módulos y main.py, sin bytecode"
for unit in \
  01-fundamentos/excepciones-personalizadas/main.py \
  02-estructuras-datos/pila/main.py \
  02-estructuras-datos/lista-simple/main.py \
  02-estructuras-datos/heaps-y-prioridades/main.py \
  02-estructuras-datos/arbol-bst/main.py \
  04-patrones-diseno/observer/basico/main.py \
  04-patrones-diseno/decorator/notificaciones/main.py \
  04-patrones-diseno/strategy/main.py \
  04-patrones-diseno/adapter/main.py; do
    python3 -B "$base/python/$unit" >/dev/null
done

echo "Resto de lenguajes: smoke tests reales"
node "$base/javascript/03-algoritmos/cuatro-reinas/cuatro_reinas.js" >/dev/null
go run "$base/go/01-fundamentos/goroutines-canales/goroutines_canales.go" >/dev/null
ruby "$base/ruby/03-algoritmos/bfs-grafo/bfs_grafo.rb" >/dev/null
sh -n "$base/shell/01-fundamentos/variables-y-quoting/variables_y_quoting.sh"
nasm -f elf64 "$base/nasm-x64/03-algoritmos/busqueda-lineal/busqueda_lineal.asm" -o "$tmp/a.o"
ld "$tmp/a.o" -o "$tmp/a"
"$tmp/a" >/dev/null

echo "OK: catálogo v5, 14 paquetes, 466 fuentes y clientes seleccionados"
