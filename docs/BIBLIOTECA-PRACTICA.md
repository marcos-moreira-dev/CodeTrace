# Biblioteca de práctica CodeTrace — catálogo v40

**2.122 archivos de referencia en 14 paquetes**, incorporados en la aplicación. Un ejercicio con varios archivos cuenta varias entradas; no confundir cantidad de archivos con cantidad de ejercicios.

## Organización curricular

- `00-fundamentos-especificacion-oficial/` y `01-fundamentos-en-accion/` (Java 21): fundamentos y ejemplos independientes; `01-fundamentos/` en los demás lenguajes.
- `02-estructuras-datos/`: pilas, colas, listas, heaps, tablas, grafos y su implementación.
- `03-algoritmos/`: búsqueda, ordenamiento, recursión, grafos, backtracking, voraces y programación dinámica. **Complejidad temporal y espacial integrada**, sin carpeta separada.
- `04-patrones-diseno/`: contratos, Strategy, Observer, Factory y otros patrones donde tengan sentido; organización idiomática en C/C++ y lenguajes funcionales.

No se crean carpetas `proyectos/`, `laboratorios/` o `complejidad/`, ni se imponen patrones GoF en CMD o ensamblador. Las subcarpetas expresan problemas reales y mantienen archivos relacionados juntos.

## Cómo estudiar una unidad

El archivo cliente contiene la introducción conceptual más generosa: **propósito, problema, archivos relacionados, precondiciones, decisiones y costos**. Sus dependencias documentan contratos e invariantes, evitando comentarios línea por línea. No se fuerza MVC en ejercicios de consola.

Los comentarios iniciales de cada archivo registran versión/estándar y comandos orientativos de compilación, verificación o ejecución. Los comandos de los clientes multiarchivo deben compilar conjuntamente todas sus fuentes. Ejecútalos desde una **copia de trabajo**, no sobre referencias insustituibles.

### Casos relevantes

- Java: `01-fundamentos-en-accion/comparadores/`, `01-fundamentos-en-accion/streams/`, `03-algoritmos/programacion-dinamica/`, `02-estructuras-datos/heaps/`, `04-patrones-diseno/strategy-tarifas/`.
- Python: generadores, decoradores, cola circular, monedas y Strategy.
- C: punteros, callbacks, pila opaca con `.h/.c`, enlace estático/compartido, búsqueda binaria.
- C++: RAII, STL, iteradores, cola de prioridad y Strategy multiarchivo.
- TypeScript: tipos discriminados, comparadores genéricos, cola circular y monedas.
- JavaScript: closures, microtareas, backtracking y Strategy funcional.
- Kotlin, C#, Go, Ruby, PowerShell, Shell, CMD, NASM: características idiomáticas y ejemplos acotados.

**Ejemplo de decisión algorítmica:** para denominaciones 1, 3 y 4 y monto 6, el voraz mayor-primero elige 4+1+1 (tres monedas), mientras DP encuentra 3+3 (dos). La tabla de DP requiere O(m*k) tiempo y O(m) memoria para monto m y k denominaciones.

## Conservación y seguridad

Los ejemplos se cargan como **recursos internos**, sin conexión ni compiladores. CodeTrace practica sin sobrescribir el contenido original; el progreso se guarda por separado en `.ctrace`. La compilación externa requiere la toolchain correspondiente y puede escribir archivos, por lo que se recomienda una copia. Los programas NASM de ELF64 requieren Linux/WSL.

Fuentes de orientación: los repositorios personales Estructuras-de-Datos-y-Algoritmos, Patrones-de-dise-o-Java y PrincipiosSOLID, además del libro facilitado *De las matemáticas a la computadora*. Las implementaciones integradas deben validarse contra sus comandos y contratos; una fuente original con complejidad anotada no se considera automáticamente correcta.

**Estado:** Java integral 06–20 implementado, en revisión en `feature/java21-jls-fundamentos` (PR #7). 523 fuentes y dos documentos nuevos, 320 clientes verificables mediante `scripts/verificar-java-integral.py`. El catálogo contiene también el contenido histórico de los demás lenguajes. Las ampliaciones siguientes de esta página son históricas. Publicación y merge pendientes de CI y aceptación manual.

## Ampliación de cobertura (347 fuentes)

- Java 21: records, enum, Optional, genéricos con límites, iteradores, NIO.2,
  recursos, hilos virtuales, vector dinámico, tabla hash, BST y grafo con
  adyacencias. Algoritmos Merge Sort, BFS/DFS, 4 reinas, intervalos, mochila
  y Fibonacci memoizado. Adapter y Decorator con clientes explicativos.
- C17: punteros dobles, realloc, structs, bits y callbacks de qsort; cola,
  lista enlazada y hash; Merge Sort, BFS sobre matriz, mochila y 4 reinas.
- C++20: concepts, movimiento y propiedad, optional/variant, span, BST,
  grafo, Merge Sort, BFS/DFS, mochila, 4 reinas y patrones.
- Python 3.12: dataclasses, context managers, Protocol, funciones superiores,
  BST, heap, ordenamiento, recorridos, backtracking, mochila, voraces y patrones.
- TypeScript: tipos condicionales, unions exhaustivas, async/await, readonly,
  heap, BST, Merge Sort, BFS/DFS, mochila y patrones.
- Kotlin, C#, JavaScript, Go y Ruby: ampliación idiomática de funciones,
  colecciones, concurrencia/asincronía, grafos, heaps y DP.
- PowerShell, POSIX shell, CMD y NASM: ejercicios de sistemas, objetos,
  procesado de registros, ciclos y memoria pertinentes a cada lenguaje.

**Nota:** seguimos diferenciando archivos físicos de ejercicios completos.
Esta ampliación es sustancial, pero no convierte la biblioteca en un curso
exhaustivo ni certifica todas las posibilidades del temario maestro.

## Reorganización semántica v5

Todos los fuentes de práctica están ubicados al menos tres niveles por debajo del paquete: `categoría/unidad/archivo`. Los patrones y algoritmos independientes tienen carpeta propia. Se mantienen diferenciados `observer/basico/` (ejemplo mínimo) y `observer/` (ejemplo multiarchivo). La fábrica simple **no** equivale a Factory Method.

**Grafos Java:** `02-estructuras-datos/grafos/tipos/` distingue dirigido/no dirigido, ponderado/no ponderado, DAG, árbol, completo, bipartito y componentes. `02-estructuras-datos/grafos/representaciones/` contrasta matriz O(V²) y lista O(V+E). En `03-algoritmos/grafos/` viven recorridos BFS/DFS y rutas por saltos, Dijkstra (pesos no negativos), Bellman–Ford (admite pesos negativos sin ciclos negativos alcanzables), Floyd–Warshall (todos los pares), Prim, Kruskal, Union-Find y orden topológico. **MST y caminos mínimos resuelven problemas diferentes.**

Los comentarios conceptuales más completos están en los clientes; no se traslada código original de GitHub de forma indiscriminada. La migración exacta de los archivos originales se registra en `docs/MAPA-REORGANIZACION-BIBLIOTECA-V4.tsv`.

La caché cambia a `v5` para no mezclar referencias antiguas con las nuevas. Las copias `v2`/`v3` del usuario y sus sesiones existentes **no se eliminan**. Esta rama continúa siendo un borrador sujeto a QA.

### Grafos: comparación entre lenguajes

Java incluye grafos genéricos con `Vertice<T>`, igualdad por id y latencias; Kosaraju para componentes fuertemente conexas; y validación de ciclos negativos y destinos inaccesibles. Python contrasta tipos dirigidos/no dirigidos, Dijkstra con `heapq`, Floyd–Warshall y Prim/Kruskal. TypeScript aporta contratos de grafos con `Map`, ordenamiento de DAG, Dijkstra cuadrático sin heap y Kruskal con conjuntos disjuntos. C++ agrega Kruskal, Kahn y Floyd–Warshall usando STL. Estas variantes resaltan decisiones idiomáticas, no son copias indiscriminadas entre lenguajes.

### Auditoría local Windows

Desde la raíz del repositorio se puede ejecutar `scripts/auditar-biblioteca.ps1` para revisar los 387 recursos, 14 lenguajes, carpetas y cabeceras sin instalar compiladores. Con `-EjecutarClientes` se compila el paquete Java 21 en TEMP y se ejecutan ejemplos de grafos y patrones. El script elimina ese directorio temporal al terminar y no modifica las referencias.

## Ampliación de miniproyectos comparables v5

Java incorpora excepciones de negocio checked/unchecked y fallos suprimidos en AutoCloseable. Kotlin enseña fun interface, null safety y excepciones propias; C# agrega eventos reales, Strategy, Factory Method, Adapter, Decorator y colecciones genéricas; TypeScript separa contratos en módulos y clientes Main; Python utiliza Protocol, funciones y main.py; C++ utiliza .hpp/.cpp, unique_ptr y RAII. C y C++ contienen `01-fundamentos/compilacion-y-enlazado/biblioteca-estatica-dinamica/` con compilación/enlace de bibliotecas .a y .so en Linux/WSL. Ninguna variante requiere MVC para estos ejemplos de consola.

## Ampliación orientada a objetos y paradigmas nativos (v6)

Ruby 3.3 incluye clientes independientes para Factory Method, Strategy con bloques, Adapter con duck typing, Decorator por composición, Observer con suscripción/desuscripción, pila, cola circular, BST y excepción propia. JavaScript ES2022/Node 24 incorpora clientes CommonJS de esos cinco patrones con closures, callbacks y objetos compatibles por contrato dinámico, más cola circular. Se mantienen ejemplos separados de JS event loop, map/set, promesas y generadores. La biblioteca tiene **492 fuentes** en **14 lenguajes** y se estudia en consola; estas cifras no equivalen al número de ejercicios autónomos.

## Revisión v8 — paridad curricular de ocho lenguajes orientados a objetos

Se integran **813 archivos** en 14 paquetes. Java 21 es el modelo de referencia para listas enlazadas simple, doble, circular y doble circular; cola enlazada; deque; AVL; Trie; Union-Find; heap máximo; tabla hash por sondeo lineal; y grafos dirigidos ponderados. Se crean miniproyectos comparables en **Java, Kotlin, C#, TypeScript, Python, C++, Ruby y JavaScript**, sin renunciar a las peculiaridades de cada idioma (Javadoc, KDoc, TSDoc, docstrings, C# XML, RAII, duck typing, null safety). 

Se añaden **cinco patrones GoF**: Builder, Abstract Factory, Composite, Command y Template Method, con cliente propio y dominio o interfaz cuando agrega valor. Es importante distinguir Factory Method de Abstract Factory, Strategy de Template Method y las listas lineales de las circulares. El cliente presenta contexto, contratos, invariantes, casos límite y complejidad. No imponer MVC a miniejercicios de consola.

Se unifica `02-estructuras-datos/linked-list/simple/` para dejar juntas las variantes y evitar un `lista-simple/` paralelo. El catálogo se regenera a partir de los archivos reales; se valida su consistencia y se compilan los clientes por grupos. Los otros seis idiomas conservan un temario propio en vez de copiar indiscriminadamente las jerarquías Java.

## v8 — salida esperada y nuevos subtemas de algoritmos

- Los puntos de entrada y clases clientes reconocibles terminan con un comentario de **salida esperada**. Si la salida contiene variables, se documentan los fragmentos literales o el tipo de resultado, sin inventar valores exactos. Se espera finalizacion sin errores de asercion. El cliente conserva la cabecera conceptual y los comandos hipoteticos.
- En Java: **Bubble Sort optimizado**, **Insertion Sort**, **Merge Sort**, **HeapSort**, **QuickSort** (comparativa compartiendo una misma entrada); **Binary Insertion Sort** (menos comparaciones, pero movimientos cuadraticos) y **Lower/Upper Bound** (posiciones de insercion y rangos repetidos). Son temas distintos. Se incluyen casos vacios y repetidos y algunas pruebas adversas.
- Se replican los escenarios en Python, TypeScript, Kotlin, C#, C++, Ruby y JavaScript con idiomatismos de lenguaje. El libro `De las matematicas a la computadora` sustenta los cinco sorts y la distincion entre complejidad, estabilidad e in-place; el repo AlgoKit aporta Upper/Lower Bound y cuatro sorts.
- No confundir `813 archivos` con `813 ejercicios` ni el exito de una prueba funcional con una medicion valida de complejidad temporal.

## Fundamentos de Java 21

La carpeta `java21/01-fundamentos-en-accion/` conserva los 28 archivos originales. La nueva `java21/00-fundamentos-especificacion-oficial/` contiene adaptaciones de la JLS 21, con unidades independientes, casos de rechazo intencional y compilaciones sucesivas. No compilar todo `java21/` de una vez: para ejemplos anteriores, excluir la nueva carpeta; para JLS, seguir los comandos por unidad o usar `python3 scripts/verificar-jls21.py`. Las cachés de versiones previas se conservan; queda pendiente comprobar la reapertura de sesiones reales de la versión anterior.

### Compilación de ejemplos Java anteriores

Desde la carpeta `java21` en PowerShell, excluir los nuevos ejemplos independientes al compilar los ejemplos históricos:

```powershell
$fuentes = Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }
$out = Join-Path $env:TEMP 'ct-java21'
New-Item -ItemType Directory -Force $out | Out-Null
javac --release 21 -encoding UTF-8 -d $out $fuentes.FullName
```

Las unidades nuevas se compilan desde su carpeta siguiendo sus propios comandos. No mezclar las variantes incompatibles ni los casos de rechazo intencional. La caché v18 conserva las anteriores.

## Fundamentos de TypeScript 7

El catálogo v40 incluye las 334 rutas del nuevo bloque (459 archivos en TypeScript; 2456 en los 14 paquetes). Se preservan los 125 archivos TypeScript anteriores y las 1329 entradas Java. Los 296 casos se verifican independientemente con TypeScript 7.0.2 y Node 24; no deben compilarse junto a los ejercicios legacy de TypeScript 5.9. Incluye negativos deliberados, declaraciones, configuraciones y ejemplos para navegador. Los TSV son documentación consultable; los .cts se reconocen como TypeScript. La atribución está en el propio bloque. Comandos de consola directos, sin automatización obligatoria.
