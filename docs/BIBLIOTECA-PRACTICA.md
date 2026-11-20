# Biblioteca de práctica CodeTrace — catálogo v2

**226 archivos de referencia en 14 paquetes**, incorporados en la aplicación. Un ejercicio con varios archivos cuenta varias entradas; no confundir cantidad de archivos con cantidad de ejercicios.

## Organización curricular

- `01-fundamentos/`: sintaxis, tipos, control de flujo, funciones y especialidades del lenguaje.
- `02-estructuras-datos/`: pilas, colas, listas, heaps, tablas, grafos y su implementación.
- `03-algoritmos/`: búsqueda, ordenamiento, recursión, grafos, backtracking, voraces y programación dinámica. **Complejidad temporal y espacial integrada**, sin carpeta separada.
- `04-patrones-diseno/`: contratos, Strategy, Observer, Factory y otros patrones donde tengan sentido; organización idiomática en C/C++ y lenguajes funcionales.

No se crean carpetas `proyectos/`, `laboratorios/` o `complejidad/`, ni se imponen patrones GoF en CMD o ensamblador. Las subcarpetas expresan problemas reales y mantienen archivos relacionados juntos.

## Cómo estudiar una unidad

El archivo cliente contiene la introducción conceptual más generosa: **propósito, problema, archivos relacionados, precondiciones, decisiones y costos**. Sus dependencias documentan contratos e invariantes, evitando comentarios línea por línea. No se fuerza MVC en ejercicios de consola.

Los comentarios iniciales de cada archivo registran versión/estándar y comandos orientativos de compilación, verificación o ejecución. Los comandos de los clientes multiarchivo deben compilar conjuntamente todas sus fuentes. Ejecútalos desde una **copia de trabajo**, no sobre referencias insustituibles.

### Casos relevantes

- Java: `01-fundamentos/comparadores/`, `01-fundamentos/streams/`, `03-algoritmos/programacion-dinamica/`, `02-estructuras-datos/heaps/`, `04-patrones-diseno/strategy-tarifas/`.
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

**Estado:** desarrollo en rama `feature/practice-curriculum-20261120`. Publicación/merge sujetos a aceptación manual y condiciones de `CIERRE-V0.3.md`.
