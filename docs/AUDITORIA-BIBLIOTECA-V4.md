# Auditoría de estructura curricular — CodeTrace biblioteca v6

**Rama:** `feature/practice-curriculum-20261120` (PR #2, borrador).  
**Referencia de auditoría:** recursos en `src/main/resources/practice/`.  
**Estado:** integración automatizada, pendiente de aceptación manual Windows.

## Resumen verificable

- **492 archivos fuente** en **14 lenguajes**.
- **295 carpetas (referencia v6) de último nivel con código**, que NO deben confundirse con 295 ejercicios independientes (algunos clientes comparten unidad, y ciertas carpetas contienen varias variantes).
- **197 archivos originales sueltos** fueron trasladados a carpetas, y se ajustaron rutas adicionales de ejercicios relacionados; hay **202 correspondencias** de rutas antiguas a nuevas en `MAPA-REORGANIZACION-BIBLIOTECA-V4.tsv`.
- **0 archivos de código directamente bajo las cuatro raíces curriculares**. Cada fuente pertenece a una carpeta de unidad; los archivos de implementación conservan al cliente relacionado en la misma unidad cuando corresponde.
- La biblioteca creció de **347 a 492 fuentes**: 40 fuentes nuevas en esta iteración, especialmente grafos y clientes de patrones.
- El catálogo es `v6`, separado de cachés `v2/v3` para no mezclar rutas. No se eliminan sesiones previas.

## Distribución por lenguaje y categoría

| Lenguaje | Fuentes | Fundamentos | Estructuras | Algoritmos | Patrones | Carpetas con código |
|---|---:|---:|---:|---:|---:|---:|
| `java21` | 89 | 17 | 21 | 27 | 24 | 47 |
| `typescript` | 46 | 13 | 11 | 13 | 9 | 33 |
| `python` | 39 | 8 | 8 | 14 | 9 | 30 |
| `c` | 30 | 9 | 7 | 7 | 7 | 23 |
| `cpp` | 32 | 8 | 4 | 11 | 9 | 28 |
| `kotlin` | 31 | 11 | 4 | 6 | 10 | 23 |
| `csharp` | 19 | 7 | 2 | 5 | 5 | 17 |
| `javascript` | 18 | 7 | 2 | 5 | 4 | 17 |
| `go` | 17 | 6 | 2 | 5 | 4 | 15 |
| `ruby` | 14 | 5 | 2 | 4 | 3 | 13 |
| `powershell` | 13 | 5 | 2 | 3 | 3 | 12 |
| `shell` | 13 | 9 | 0 | 4 | 0 | 12 |
| `cmd` | 13 | 9 | 0 | 4 | 0 | 12 |
| `nasm-x64` | 13 | 6 | 1 | 6 | 0 | 13 |

*Los números son archivos, no prácticas independientes. Un patrón de cinco clases contribuye cinco fuentes y una sola carpeta de unidad.*

## Organización: criterios aplicados

1. **Patrones:** Factory simple y Factory Method son unidades distintas; Observer y Strategy tienen variantes mínimas y multiarchivo diferenciadas. No se fuerzan patrones orientados a objetos en CMD, shell ni NASM.
2. **Estructuras de datos:** cada implementación de pila, cola, BST, heap, hash o grafo se agrupa en su carpeta. Donde existe separación cliente/implementación se conservan ambos archivos juntos.
3. **Algoritmos:** búsquedas, Euclides, ordenamientos, backtracking, programación voraz, DP y grafos poseen unidades propias; caminos sin peso están separados del orden de visita BFS/DFS, y mochila de cambio de monedas.
4. **Fundamentos:** se mantienen subtemas idiomáticos dentro del lenguaje (módulos, tipos, funciones, sintaxis), sin obligar a todos los lenguajes a enseñar los mismos rasgos.
5. **Documentación:** los clientes explican propósito, contratos, opciones algorítmicas y complejidad con supuestos. Los encabezados indican versiones y comandos hipotéticos; algunos componentes auxiliares solo se compilan con su cliente.

## Grafos: cobertura semántica

| Pregunta | Unidad principal |
|---|---|
| ¿Qué diferencia dirigido, no dirigido, ponderado, DAG, bipartito o árbol? | Java `02-estructuras-datos/grafos/tipos`; Python/TS con versiones idiomáticas |
| ¿Cómo representa un grafo la memoria? | Java `02-estructuras-datos/grafos/representaciones` (matriz O(V²), lista O(V+E)) |
| ¿Cómo preservar identidad estable con payload genérico? | Java `02-estructuras-datos/grafos/genericos` |
| ¿Qué diferencia BFS y DFS? | Java `03-algoritmos/grafos/recorridos/orden-visita` y clientes Python/TS/C++ |
| ¿Por qué BFS encuentra un camino sin pesos? | Java `03-algoritmos/grafos/recorridos/caminos-sin-peso` |
| ¿Cuándo utilizar Dijkstra frente a Bellman-Ford? | Java `03-algoritmos/grafos/caminos-minimos`, Python Dijkstra y TS Dijkstra |
| ¿Cómo resolver caminos entre todos los pares? | Java/Python/C++ con Floyd–Warshall |
| ¿Qué diferencia un MST de un camino mínimo? | Java/Python/C++/TS con Prim/Kruskal y Union-Find |
| ¿Cómo ordenar tareas en un DAG o detectar un ciclo? | Java/TS/C++ con Kahn |
| ¿Cómo identificar conectividad fuerte dirigida? | Java con Kosaraju |

El repositorio propio `Estructuras-de-Datos-y-Algoritmos` sirvió de orientación conceptual: Graph/Vertex/Edge, BFS/DFS, Dijkstra, Floyd–Warshall, Prim, Kruskal y Union-Find. Se evitó trasladar sin auditoría el caso de Dijkstra que utilizaba `PriorityQueue.remove(v)` y anunciaba una complejidad de heap que no respetaba esa eliminación. La implementación nueva de Dijkstra emplea entradas perezosas que se ignoran al quedar obsoletas.

## Evidencia de pruebas y límites

- `BuiltInLibraryTest` exige integridad de catálogo, 14 lenguajes, `387` fuentes, carpeta por archivo y compilación conjunta de las fuentes Java 21.
- `scripts/verificar-biblioteca.sh` comprueba sintaxis/rutas, compila y ejecuta clientes representativos; los nuevos casos de grafos validan resultados y precondiciones.
- GitHub Actions `Verify practice curriculum` utiliza Ubuntu y Windows; la rama incorpora una tarea específica de Kotlin 2.2.0 con compilador oficial y checksum SHA-256.
- El empaquetado portable es un control distinto de la validación visual del árbol, iconos, expansión y ergonomía en Windows.
- **No se declara exhaustivo el temario**: quedan ejercicios posibles de AVL, árboles balanceados, grafos avanzados por otros lenguajes, pruebas de propiedades y escenarios de memoria, según la prioridad de estudio. La calidad tiene preferencia sobre duplicar algoritmos 14 veces.

## Referencias
- PR: <https://github.com/marcos-moreira-dev/CodeTrace/pull/2>
- Biblioteca: `src/main/resources/practice/catalog.tsv`
- Trazabilidad: `docs/MAPA-REORGANIZACION-BIBLIOTECA-V4.tsv`
- Versión de comandos: `docs/LENGUAJES-Y-COMANDOS.md`
- Auditoría de cierre de aplicación: `docs/CIERRE-V0.3.md`

La ampliación **no modifica `main` ni publica una versión estable**.

## Estado de la segunda revisión (v6)

La primera auditoría correspondía a 387 fuentes. La segunda llega a **492 fuentes**, incorpora clases cliente para excepciones, colecciones y patrones en Java, Kotlin, C#, TypeScript, Python, Ruby, JavaScript y C++, y compara enlazado de bibliotecas C/C++. El inventario de carpetas previo a v6 es histórico; comprobar las rutas actuales en `catalog.tsv`. Los 14 lenguajes no tienen necesariamente iguales cantidades, porque las lecciones autóctonas difieren. La validación automatizada sólo cubre los clientes enumerados explícitamente en los scripts; la aceptación manual sigue pendiente.
