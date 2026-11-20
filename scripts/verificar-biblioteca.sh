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
assert len(items) == 387, len(items)
assert len({x[0] for x in items}) == 14
assert all(len(path.split("/")) >= 3 for _, path in items), "Loose file in category root"
for pack, path in items:
    source = base / pack / path
    assert source.is_file(), source
    lines = source.read_text(encoding="utf-8").splitlines()[:10]
    assert any("CodeTrace" in x for x in lines), source
    assert any("referencia:" in x.lower() or "kotlin 2.2" in x.lower() for x in lines), source
    assert any(any(term in x.lower() for term in ("compilar", "comprobar", "ejecutar", "verificar")) for x in lines), source
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
node "$base/javascript/03-algoritmos/cuatro-reinas/cuatro_reinas.js"
go run "$base/go/01-fundamentos/goroutines-canales/goroutines_canales.go"
ruby -c "$base/ruby/01-fundamentos/bloques-y-enumerables/bloques_y_enumerables.rb"
sh -n "$base/shell/01-fundamentos/variables-y-quoting/variables_y_quoting.sh"
sh "$base/shell/03-algoritmos/frecuencia-de-palabras/frecuencia_de_palabras.sh"

nasm -f elf64 "$base/nasm-x64/03-algoritmos/busqueda-lineal/busqueda_lineal.asm" -o "$tmp/busqueda.o"
ld "$tmp/busqueda.o" -o "$tmp/busqueda"
"$tmp/busqueda"
echo "Verificando ejercicios nuevos de Java y algoritmos"
for clase in \
  RecordsYEnums OptionalYResultados GenericosYContratos \
  ColeccionesEIteradores ArchivosYNio ExcepcionesYRecursos ConcurrenciaVirtual; do
    java -cp "$tmp/java" "$clase" >/dev/null
done
for clase in \
  codetrace.practica.vector.MainVector \
  codetrace.practica.hash.MainHash \
  codetrace.practica.bst.MainArbol \
  codetrace.practica.grafo.MainGrafo \
  codetrace.practica.ordenamientos.MainOrdenamientos \
  codetrace.practica.recorridos.MainRecorridos \
  codetrace.practica.reinas.CuatroReinas \
  codetrace.practica.actividades.SeleccionActividades \
  codetrace.practica.mochila.Mochila01 \
  codetrace.practica.fibonacci.FibonacciMemoizado \
  codetrace.practica.adapter.MainAdapter \
  codetrace.practica.decorator.MainDecorator \
  codetrace.practica.grafostipos.MainTiposGrafo \
  codetrace.practica.grafosrepresentaciones.MainRepresentaciones \
  codetrace.practica.recorridos.MainCaminosSinPeso \
  codetrace.practica.grafoscaminos.MainCaminosMinimos \
  codetrace.practica.grafosmst.MainExpansionMinima \
  codetrace.practica.grafostopologico.MainTopologico \
  MainFactoryBasica \
  MainObservadorBasico \
  codetrace.practica.grafosgenericos.MainGrafoGenerico \
  codetrace.practica.grafosscc.MainComponentesFuertes \
  codetrace.practica.grafoscaminos.MainCasosLimite; do
    java -cp "$tmp/java" "$clase" >/dev/null
done

echo "Compilando y ejecutando C17, incluidos punteros y estructuras"
for archivo in \
  01-fundamentos/punteros-dobles/punteros_dobles.c \
  01-fundamentos/realloc-vector/realloc_vector.c \
  01-fundamentos/structs-y-enums/structs_y_enums.c \
  01-fundamentos/bits-y-mascaras/bits_y_mascaras.c \
  01-fundamentos/qsort-callbacks/qsort_callbacks.c \
  02-estructuras-datos/cola-circular/cola_circular.c \
  02-estructuras-datos/lista-enlazada/lista_enlazada.c \
  02-estructuras-datos/tabla-hash/tabla_hash.c \
  03-algoritmos/merge-sort/merge_sort.c \
  03-algoritmos/bfs-matriz/bfs_matriz.c \
  03-algoritmos/mochila-01/mochila_01.c \
  03-algoritmos/cuatro-reinas/cuatro_reinas.c; do
    gcc -std=c17 -Wall -Wextra -Werror "$base/c/$archivo" -o "$tmp/c-extra"
    "$tmp/c-extra" >/dev/null
done

echo "Compilando y ejecutando C++20 idiomático"
for archivo in \
  01-fundamentos/templates-y-concepts/templates_y_concepts.cpp \
  01-fundamentos/semantica-movimiento/semantica_movimiento.cpp \
  01-fundamentos/optional-variant/optional_variant.cpp \
  01-fundamentos/span-y-iteradores/span_y_iteradores.cpp \
  02-estructuras-datos/arbol-bst/arbol_bst.cpp \
  02-estructuras-datos/grafo-adyacencia/grafo_adyacencia.cpp \
  03-algoritmos/merge-sort/merge_sort.cpp \
  03-algoritmos/bfs-dfs/bfs_dfs.cpp \
  03-algoritmos/mochila-01/mochila_01.cpp \
  03-algoritmos/cuatro-reinas/cuatro_reinas.cpp \
  04-patrones-diseno/adapter/unidades/adapter_unidades.cpp \
  04-patrones-diseno/decorator/notificaciones/decorator_notificaciones.cpp; do
    g++ -std=c++20 -Wall -Wextra -Werror "$base/cpp/$archivo" -o "$tmp/cpp-extra"
    "$tmp/cpp-extra" >/dev/null
done

echo "Validando TypeScript 5.9 y ejecutando clientes nuevos"
for archivo in \
  01-fundamentos/tipos-condicionales/tipos_condicionales.ts \
  01-fundamentos/discriminated-unions/discriminated_unions.ts \
  01-fundamentos/promesas-async/promesas_async.ts \
  01-fundamentos/readonly-e-inmutabilidad/readonly_e_inmutabilidad.ts \
  02-estructuras-datos/heap/Principal.ts \
  02-estructuras-datos/arbol-bst/Principal.ts \
  03-algoritmos/merge-sort/merge_sort.ts \
  03-algoritmos/bfs-dfs/bfs_dfs.ts \
  03-algoritmos/mochila-01/mochila_01.ts \
  03-algoritmos/cuatro-reinas/cuatro_reinas.ts \
  04-patrones-diseno/adapter/unidades/adapter_unidades.ts \
  04-patrones-diseno/decorator/notificaciones/decorator_notificaciones.ts; do
    tsc --strict --target ES2022 --module commonjs --noEmit "$base/typescript/$archivo"
done
for archivo in \
  02-estructuras-datos/heap/Principal.ts \
  02-estructuras-datos/arbol-bst/Principal.ts; do
    tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts-extra" \
      "$base/typescript/$archivo"
    node "$tmp/ts-extra/Principal.js" >/dev/null
done

echo "Ejecutando Python, JavaScript, Go y Ruby idiomáticos"
for archivo in \
  01-fundamentos/dataclasses-y-propiedades/dataclasses_y_propiedades.py \
  01-fundamentos/context-managers/context_managers.py \
  01-fundamentos/protocolos-y-genericos/protocolos_y_genericos.py \
  01-fundamentos/funciones-orden-superior/funciones_orden_superior.py \
  02-estructuras-datos/arbol-bst/main.py \
  02-estructuras-datos/heaps-y-prioridades/heaps_y_prioridades.py \
  03-algoritmos/merge-sort/merge_sort.py \
  03-algoritmos/bfs-dfs/bfs_dfs.py \
  03-algoritmos/cuatro-reinas/cuatro_reinas.py \
  03-algoritmos/mochila-01/mochila_01.py \
  03-algoritmos/seleccion-intervalos/seleccion_intervalos.py \
  04-patrones-diseno/adapter/main.py \
  04-patrones-diseno/decorator/notificaciones/decorator_objetos.py; do
    python3 -B "$base/python/$archivo" >/dev/null
done
for archivo in \
  01-fundamentos/generadores-iteradores/generadores_iteradores.js \
  01-fundamentos/map-set-referencias/map_set_referencias.js \
  01-fundamentos/promesas-asincronas/promesas_asincronas.js \
  02-estructuras-datos/heap-minimo/heap_minimo.js \
  03-algoritmos/bfs-dfs/bfs_dfs.js \
  03-algoritmos/mochila-01/mochila_01.js; do
    node --check "$base/javascript/$archivo"
    node "$base/javascript/$archivo" >/dev/null
done
for archivo in \
  01-fundamentos/errores-y-defer/errores_y_defer.go \
  01-fundamentos/slices-y-capacidad/slices_y_capacidad.go \
  02-estructuras-datos/heap-minimo/heap_minimo.go \
  03-algoritmos/bfs-grafo/bfs_grafo.go \
  03-algoritmos/mochila-01/mochila_01.go; do
    go run "$base/go/$archivo" >/dev/null
done
for archivo in \
  01-fundamentos/enumeradores-perezosos/enumeradores_perezosos.rb \
  02-estructuras-datos/arbol-bst/arbol_bst.rb \
  03-algoritmos/bfs-grafo/bfs_grafo.rb \
  03-algoritmos/mochila-01/mochila_01.rb; do
    ruby -c "$base/ruby/$archivo" >/dev/null
    ruby "$base/ruby/$archivo" >/dev/null
done

echo "Comprobando NASM y POSIX sh"
for archivo in \
  03-algoritmos/busqueda-binaria/busqueda_binaria.asm \
  01-fundamentos/memoria-y-salida/memoria_y_salida.asm; do
    nasm -f elf64 "$base/nasm-x64/$archivo" -o "$tmp/extra.o"
    ld "$tmp/extra.o" -o "$tmp/extra"
    "$tmp/extra" >/dev/null
done
for archivo in \
  01-fundamentos/argumentos-getopts/argumentos_getopts.sh \
  03-algoritmos/seleccion-registros/seleccion_registros.sh; do
    sh -n "$base/shell/$archivo"
    sh "$base/shell/$archivo" >/dev/null
done

echo "Validando grafos multilenguaje con casos de prueba"
python3 -B "$base/python/02-estructuras-datos/grafos/tipos/main.py" >/dev/null
python3 -B "$base/python/03-algoritmos/grafos/dijkstra/dijkstra.py" >/dev/null
python3 -B "$base/python/03-algoritmos/grafos/floyd-warshall/floyd_warshall.py" >/dev/null
python3 -B "$base/python/03-algoritmos/grafos/expansion-minima/main.py" >/dev/null

for archivo in \
  03-algoritmos/grafos/expansion-minima/kruskal.cpp \
  03-algoritmos/grafos/topologico/kahn.cpp \
  03-algoritmos/grafos/floyd-warshall/floyd_warshall.cpp; do
  g++ -std=c++20 -Wall -Wextra -Werror "$base/cpp/$archivo" -o "$tmp/cpp-graph"
  "$tmp/cpp-graph" >/dev/null
done

for archivo in \
  02-estructuras-datos/grafos/tipos/Principal.ts \
  03-algoritmos/grafos/topologico/kahn.ts \
  03-algoritmos/grafos/dijkstra/cuadratico.ts \
  03-algoritmos/grafos/expansion-minima/kruskal.ts \
  04-patrones-diseno/observer/basico/Principal.ts; do
  destino="$tmp/ts-graph"
  mkdir -p "$destino"
  tsc --strict --target ES2022 --module commonjs --outDir "$destino" \
    "$base/typescript/$archivo"
  js="$(basename "$archivo" .ts).js"
  node "$destino/$js" >/dev/null
done

echo "OK: catálogo v4, 14 lenguajes y casos de grafos comprobados"
