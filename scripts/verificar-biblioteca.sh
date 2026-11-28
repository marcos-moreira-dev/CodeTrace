#!/usr/bin/env bash
# CodeTrace | Referencia: biblioteca de práctica v15 / verificador GNU/Linux-WSL
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
assert len(items)==1260, len(items)
assert len({p for p,_ in items})==14
assert len(set(map(tuple,items)))==len(items)
# The integrator intentionally lives in the 05 root; all pattern
# components still require their own named exercise directory.
assert all(len(path.split("/"))>=3 or
           (pack, path) in {
               ("java21","05-patrones-diseno-counter-strike/MainCounterStrike.java"),
               ("python","05-patrones-diseno-counter-strike/main.py"),
               ("csharp","05-patrones-diseno-counter-strike/MainCounterStrike.cs"),
               ("cpp","05-patrones-diseno-counter-strike/main.cpp"),
               ("typescript","05-patrones-diseno-counter-strike/MainCounterStrike.ts")}
           for pack,path in items)
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
# Documentar la salida de TODOS los clientes identificables (sin suponer que cada fuente es cliente).
import re
clientes=[x for x in root.rglob("*") if x.is_file() and
    re.search(r"/(?:Main[A-Z][^/]*\.(?:java|kt|ts|cs)|main\.(?:py|rb|js|go|cpp|c))$",
              x.as_posix())]
assert len(clientes)>=260, len(clientes)
sin_salida=[str(x) for x in clientes
            if "Salida esperada" not in x.read_text(encoding="utf-8")[-1200:]]
assert not sin_salida, ("Clientes sin salida esperada:",sin_salida)
# Java es la referencia curricular: no admitir salidas vacías ni plantillas genéricas.
java_clientes=[x for x in clientes if x.suffix==".java" and "java21" in x.parts]
plantillas=("Se imprime el valor o coleccion calculado",
           "(los valores variables completan los fragmentos anteriores)",
           "Sin salida de texto; las pruebas del cliente se cumplen.")
no_concretos=[str(x) for x in java_clientes
              if any(marca in x.read_text(encoding="utf-8")[-1500:] for marca in plantillas)]
assert not no_concretos, ("Salidas Java genéricas o incorrectas:",no_concretos)
assert len(java_clientes)>=50, len(java_clientes)
# Los cinco patrones nuevos tienen un cliente equivalente en ocho lenguajes.
# No bastan etiquetas «Salida esperada»: rechazar contenido de relleno.
patrones_gof=(
    "04-patrones-diseno/builder/solicitud/",
    "04-patrones-diseno/abstract-factory/tema-consola/",
    "04-patrones-diseno/composite/arbol-archivos/",
    "04-patrones-diseno/command/deshacer/",
    "04-patrones-diseno/template-method/reportes/",
)
clientes_gof=[x for x in clientes if any(unidad in x.as_posix() for unidad in patrones_gof)]
assert len(clientes_gof)==40, ("Paridad GoF incompleta:",len(clientes_gof))
plantillas_gof=plantillas+(
    "Se muestran los datos o el resultado calculado del ejemplo.",
    "(los valores variables completan los fragmentos mostrados)",
)
genericos_gof=[str(x) for x in clientes_gof
              if any(marca in x.read_text(encoding="utf-8")[-1500:] for marca in plantillas_gof)]
assert not genericos_gof, ("Salidas GoF sin concretar:", genericos_gof)
print("OK catálogo:",len(items), "| clientes documentados:", len(clientes),
      "| clientes Java sin plantillas genéricas:",len(java_clientes),
      "| clientes GoF concretos:",len(clientes_gof))
PY

echo "Java 21: clases, contratos y clientes"
find "$base/java21" -path '*/00-fundamentos-especificacion-oficial' -prune -o -name '*.java' -print0 | xargs -0 javac --release 21 -encoding UTF-8 -d "$tmp/java"
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

# Ejecutar todos los clientes Java, no solo la muestra histórica anterior.
# Cada aplicación es autocontenida y no debe pedir datos ni mutar referencias.
python3 - "$base/java21" "$tmp/java" <<'PY'
from pathlib import Path
import re, subprocess, sys
root=Path(sys.argv[1])
classpath=sys.argv[2]
clientes=sorted(p for p in root.rglob("Main*.java") if "00-fundamentos-especificacion-oficial" not in p.parts)
for archivo in clientes:
    texto=archivo.read_text(encoding="utf-8")
    paquete=re.search(r"(?m)^\s*package\s+([\w.]+)\s*;", texto)
    clase=(paquete.group(1)+"." if paquete else "")+archivo.stem
    resultado=subprocess.run(["java","-cp",classpath,clase],
                             capture_output=True,text=True,encoding="utf-8",
                             timeout=20,check=False)
    if resultado.returncode!=0:
        raise AssertionError(f"{clase}: exit={resultado.returncode}\n{resultado.stderr}")
assert len(clientes)>=50, len(clientes)
print("OK: ejecutados",len(clientes),"clientes Java 21")
PY

python3 scripts/verificar-jls21.py

echo "Counter-Strike: ejecutar y comparar salidas de los 13 patrones Java"
python3 - "$tmp/java" <<'PY'
import json
from pathlib import Path
import re, subprocess, sys
classpath=sys.argv[1]
folder=Path("src/main/resources/practice/java21/05-patrones-diseno-counter-strike")
manifest=json.loads(Path("scripts/salidas_practica.json").read_text(encoding="utf-8"))
clients=sorted(p for p in folder.rglob("Main*.java")
               if p.parent != folder)
assert len(clients)==13, ("Se esperan 13 clientes de patrones",len(clients))
for source in clients:
    body=source.read_text(encoding="utf-8")
    match=re.search(r"(?m)^package\s+([\w.]+);",body)
    assert match, source
    cls=match.group(1)+"."+source.stem
    expected=manifest["java21/"+source.relative_to(folder.parent).as_posix()]
    run=subprocess.run(["java","-cp",classpath,cls],capture_output=True,
                       text=True,encoding="utf-8",timeout=15,check=True)
    assert run.stdout.strip()==expected.strip(),(cls,expected,run.stdout)
    assert not run.stderr.strip(),(cls,run.stderr)
print("OK: 13 clientes Java de Counter-Strike con salidas verificadas")

# El Main de la raíz no es un patrón independiente. Ejecuta los componentes
# de los patrones durante tres rondas y verificamos toda su narración.
integrador=folder / "MainCounterStrike.java"
source=integrador.read_text(encoding="utf-8")
match=re.search(r"(?m)^package\s+([\w.]+);", source)
assert match, integrador
cls=match.group(1)+"."+integrador.stem
run=subprocess.run(["java","-cp",classpath,cls],capture_output=True,
                   text=True,encoding="utf-8",timeout=15,check=True)
expected=manifest["java21/"+integrador.relative_to(folder.parent).as_posix()]
assert run.stdout.strip()==expected.strip(),(cls,expected,run.stdout)
assert not run.stderr.strip(),(cls,run.stderr)
print("OK: MainCounterStrike, tres rondas narradas, semilla fija y salida exacta")
PY

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
 02-estructuras-datos/linked-list/simple/MainListaSimple.ts \
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
  02-estructuras-datos/linked-list/simple/main.py \
  02-estructuras-datos/heaps-y-prioridades/main.py \
  02-estructuras-datos/arbol-bst/main.py \
  04-patrones-diseno/observer/basico/main.py \
  04-patrones-diseno/decorator/notificaciones/main.py \
  04-patrones-diseno/strategy/main.py \
  04-patrones-diseno/adapter/main.py; do
    python3 -B "$base/python/$unit" >/dev/null
done


echo "JavaScript: patrones y cola circular"
for dir in \
  04-patrones-diseno/factory-method/exportadores \
  04-patrones-diseno/strategy/tarifas \
  04-patrones-diseno/adapter/unidades \
  04-patrones-diseno/decorator/notificaciones \
  04-patrones-diseno/observer/basico \
  02-estructuras-datos/cola-circular; do
  node --check "$base/javascript/$dir/main.js"
  node "$base/javascript/$dir/main.js" >/dev/null
done

echo "Ruby: patrones y estructuras como miniproyectos"
for dir in \
  04-patrones-diseno/factory-method/exportadores \
  04-patrones-diseno/strategy/tarifas \
  04-patrones-diseno/adapter/unidades \
  04-patrones-diseno/decorator/notificaciones \
  04-patrones-diseno/observer/basico \
  02-estructuras-datos/cola-circular \
  02-estructuras-datos/pila \
  02-estructuras-datos/arbol-bst \
  01-fundamentos/excepciones-personalizadas; do
  ruby -c "$base/ruby/$dir/main.rb" >/dev/null
  ruby "$base/ruby/$dir/main.rb" >/dev/null
done


echo "v15: Java 21 — pruebas de las nuevas familias y patrones"
for clase in \
  codetrace.practica.estructuras.listadoble.MainListaDoble \
  codetrace.practica.estructuras.listacircular.MainListaCircular \
  codetrace.practica.estructuras.listadoblecircular.MainListaDobleCircular \
  codetrace.practica.estructuras.colaenlazada.MainColaEnlazada \
  codetrace.practica.estructuras.deque.MainDeque \
  codetrace.practica.estructuras.avl.MainArbolAVL \
  codetrace.practica.estructuras.trie.MainTrie \
  codetrace.practica.estructuras.unionfind.MainUnionFind \
  codetrace.practica.estructuras.heapmaximo.MainHeapMaximo \
  codetrace.practica.estructuras.hashabierto.MainHashAbierto \
  codetrace.practica.estructuras.grafoponderado.MainGrafoPonderado \
  codetrace.practica.patrones.builder.MainBuilder \
  codetrace.practica.patrones.abstractfactory.MainAbstractFactory \
  codetrace.practica.patrones.composite.MainComposite \
  codetrace.practica.patrones.command.MainCommand \
  codetrace.practica.patrones.templatemethod.MainTemplateMethod; do
  java -cp "$tmp/java" "$clase" >/dev/null
done

echo "v15: C++20 — miniproyectos completos por carpeta"
for unidad in \
  02-estructuras-datos/linked-list/simple \
  02-estructuras-datos/linked-list/doble \
  02-estructuras-datos/linked-list/circular \
  02-estructuras-datos/linked-list/doble-circular \
  02-estructuras-datos/cola-enlazada \
  02-estructuras-datos/deque \
  02-estructuras-datos/arbol-avl \
  02-estructuras-datos/trie \
  02-estructuras-datos/union-find \
  02-estructuras-datos/heap-maximo \
  02-estructuras-datos/tabla-hash/direccionamiento-abierto \
  02-estructuras-datos/grafos/ponderados \
  04-patrones-diseno/builder/solicitud \
  04-patrones-diseno/abstract-factory/tema-consola \
  04-patrones-diseno/composite/arbol-archivos \
  04-patrones-diseno/command/deshacer \
  04-patrones-diseno/template-method/reportes; do
  ruta="$base/cpp/$unidad"
  g++ -std=c++20 -Wall -Wextra -Werror "$ruta/"*.cpp -o "$tmp/cpp-v15"
  "$tmp/cpp-v15" >/dev/null
done

echo "v15: TypeScript 5.9 — transpilar y ejecutar el cliente de cada unidad"
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/linked-list/simple/MainListaSimple.ts"
node "$tmp/ts/MainListaSimple.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/linked-list/doble/MainListaDoble.ts"
node "$tmp/ts/MainListaDoble.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/linked-list/circular/MainListaCircular.ts"
node "$tmp/ts/MainListaCircular.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/linked-list/doble-circular/MainListaDobleCircular.ts"
node "$tmp/ts/MainListaDobleCircular.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/cola-enlazada/MainColaEnlazada.ts"
node "$tmp/ts/MainColaEnlazada.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/deque/MainDeque.ts"
node "$tmp/ts/MainDeque.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/arbol-avl/MainArbolAVL.ts"
node "$tmp/ts/MainArbolAVL.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/trie/MainTrie.ts"
node "$tmp/ts/MainTrie.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/union-find/MainUnionFind.ts"
node "$tmp/ts/MainUnionFind.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/heap-maximo/MainHeapMaximo.ts"
node "$tmp/ts/MainHeapMaximo.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/tabla-hash/direccionamiento-abierto/MainHashAbierto.ts"
node "$tmp/ts/MainHashAbierto.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/02-estructuras-datos/grafos/ponderados/MainGrafoPonderado.ts"
node "$tmp/ts/MainGrafoPonderado.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/04-patrones-diseno/builder/solicitud/MainBuilder.ts"
node "$tmp/ts/MainBuilder.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/04-patrones-diseno/abstract-factory/tema-consola/MainAbstractFactory.ts"
node "$tmp/ts/MainAbstractFactory.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/04-patrones-diseno/composite/arbol-archivos/MainComposite.ts"
node "$tmp/ts/MainComposite.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/04-patrones-diseno/command/deshacer/MainCommand.ts"
node "$tmp/ts/MainCommand.js" >/dev/null
tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" "$base/typescript/04-patrones-diseno/template-method/reportes/MainTemplateMethod.ts"
node "$tmp/ts/MainTemplateMethod.js" >/dev/null

echo "v15: Python, Ruby y JavaScript — todos los nuevos clientes"
for unidad in \
  02-estructuras-datos/linked-list/simple \
  02-estructuras-datos/linked-list/doble \
  02-estructuras-datos/linked-list/circular \
  02-estructuras-datos/linked-list/doble-circular \
  02-estructuras-datos/cola-enlazada \
  02-estructuras-datos/deque \
  02-estructuras-datos/arbol-avl \
  02-estructuras-datos/trie \
  02-estructuras-datos/union-find \
  02-estructuras-datos/heap-maximo \
  02-estructuras-datos/tabla-hash/direccionamiento-abierto \
  02-estructuras-datos/grafos/ponderados \
  04-patrones-diseno/builder/solicitud \
  04-patrones-diseno/abstract-factory/tema-consola \
  04-patrones-diseno/composite/arbol-archivos \
  04-patrones-diseno/command/deshacer \
  04-patrones-diseno/template-method/reportes; do
  python3 -B "$base/python/$unidad/main.py" >/dev/null
  ruby -c "$base/ruby/$unidad/main.rb" >/dev/null
  ruby "$base/ruby/$unidad/main.rb" >/dev/null
  node --check "$base/javascript/$unidad/main.js"
  node "$base/javascript/$unidad/main.js" >/dev/null
done


echo "v15: comparativa de cinco sorts, insercion binaria y limites ordenados"
for clase in \
  codetrace.practica.ordenamientos.MainOrdenamientos \
  codetrace.practica.algoritmos.burbuja.MainBurbuja \
  codetrace.practica.algoritmos.insercionbinaria.MainInsercionBinaria \
  codetrace.practica.algoritmos.limites.MainLimitesOrdenados; do
  java -cp "$tmp/java" "$clase" >/dev/null
done
for unidad in \
  ordenamientos/comparativa-cinco \
  ordenamientos/insercion-binaria \
  busqueda-rangos; do
  cpp="$base/cpp/03-algoritmos/$unidad"
  g++ -std=c++20 -Wall -Wextra -Werror "$cpp/"*.cpp -o "$tmp/sort-cpp"
  "$tmp/sort-cpp" >/dev/null
  py="$base/python/03-algoritmos/$unidad/main.py"
  python3 -B "$py" >/dev/null
  ruby "$base/ruby/03-algoritmos/$unidad/main.rb" >/dev/null
  node "$base/javascript/03-algoritmos/$unidad/main.js" >/dev/null
done
for main in \
  ordenamientos/comparativa-cinco/MainOrdenamientos.ts \
  ordenamientos/insercion-binaria/MainInsercionBinaria.ts \
  busqueda-rangos/MainLimitesOrdenados.ts; do
  tsc --strict --target ES2022 --module commonjs --outDir "$tmp/ts" \
      "$base/typescript/03-algoritmos/$main"
  node "$tmp/ts/$(basename "$main" .ts).js" >/dev/null
done

echo "Resto de lenguajes: smoke tests reales"
node "$base/javascript/03-algoritmos/cuatro-reinas/cuatro_reinas.js" >/dev/null
go run "$base/go/01-fundamentos/goroutines-canales/goroutines_canales.go" >/dev/null
ruby "$base/ruby/03-algoritmos/bfs-grafo/bfs_grafo.rb" >/dev/null
sh -n "$base/shell/01-fundamentos/variables-y-quoting/variables_y_quoting.sh"
nasm -f elf64 "$base/nasm-x64/03-algoritmos/busqueda-lineal/busqueda_lineal.asm" -o "$tmp/a.o"
ld "$tmp/a.o" -o "$tmp/a"
"$tmp/a" >/dev/null

echo "Arena Táctica: mismo caso Chain of Responsibility en Java y Python"
java -cp "$tmp/java" codetrace.practica.patrones.cadena.MainCadenaResponsabilidad > "$tmp/cadena-java.txt"
python3 -B "$base/python/04-patrones-diseno/chain-of-responsibility/reportes-partida/main.py" > "$tmp/cadena-python.txt"
diff -u "$tmp/cadena-java.txt" "$tmp/cadena-python.txt"
echo "OK: salidas Java/Python idénticas para la cadena de responsabilidad"

echo "v15: comprobar documentación de consola en todas las fuentes"
python3 -B scripts/documentar_consola_practica.py --check \
  --root "$base" --outcomes scripts/salidas_practica.json
python3 - "$base" <<'PY'
from pathlib import Path
import sys
root=Path(sys.argv[1])
seen={}
for path in root.rglob("*"):
    if not path.is_file(): continue
    key=str(path.relative_to(root)).casefold()
    assert key not in seen, ("Colision de nombres Windows",seen.get(key),str(path))
    seen[key]=str(path)
print("OK: rutas de la biblioteca sin colisiones por mayusculas/minusculas")
PY

echo "OK: catálogo v15, 14 paquetes, 1260 fuentes documentadas y clientes seleccionados"
