# Biblioteca de práctica integrada (v1)

CodeTrace incluye **108 ejercicios originales en 13 paquetes** dentro del
JAR, sin descarga de repositorios ni compilación durante la mecanografía.
La fuente exacta de los recuentos es `src/main/resources/practice/catalog.tsv`.

## Paquetes y cantidades

| Paquete | Ejercicios | Referencia didáctica |
|---|---:|---|
| `java21` | 16 | Java 21 |
| `kotlin` | 16 | Kotlin 2.2 / JVM 21 |
| `typescript` | 16 | TypeScript 5.9 |
| `javascript` | 6 | ECMAScript 2022 |
| `python` | 6 | Python 3.12 |
| `c` | 6 | C17 |
| `cpp` | 6 | C++20 |
| `csharp` | 6 | C# 12 / .NET 8 |
| `go` | 6 | Go 1.24 |
| `ruby` | 6 | Ruby 3.3 |
| `powershell` | 6 | PowerShell 7.4 |
| `shell` | 6 | POSIX sh |
| `cmd` | 6 | Windows CMD |
| **Total** | **108** | **13 paquetes** |

Los ejercicios son implementaciones reducidas y originales para practicar
mecanografía, organizadas por fundamentos, tipos, funciones, estructuras de
datos, algoritmos y patrones cuando el lenguaje lo justifica. El objetivo es
equivalencia temática, no obligar a los lenguajes procedimentales o de shell
a utilizar paradigmas orientados a objetos. Se usan como referencias
conceptuales las especificaciones de lenguajes y materiales de ciencias de
la computación; no son copias literales.

## Infraestructura

- `practice/catalog.tsv`: inventario de recursos empaquetados, un archivo por línea; la primera columna identifica el paquete y la segunda es la ruta.
- `com.mmdev.codetrace.library.BuiltInLibrary`: valida el catálogo, expone los paquetes y materializa referencias en `~/.codetrace/practice-library/v1/<pack>/`.
- El contenido del JAR prevalece: al abrir un paquete, se restauran los archivos que hayan cambiado en esa copia de caché.
- La mecanografía utiliza `TypingSession`, incluidas las reglas de Turbo y progreso; las omisiones no cuentan como caracteres correctos.
- El entrenamiento no compila ni ejecuta los archivos. **Los comandos externos configurados por el usuario sí pueden modificar físicamente la caché**, por lo que no deben apuntar a esta biblioteca.
- El progreso se almacena en sesiones `.ctrace`, separadas de las plantillas.

La biblioteca funciona sin internet. Para versiones de referencia y comandos
de comprobación consulta `docs/LENGUAJES-Y-COMANDOS.md`. La validación de
todos los ejercicios con cada toolchain externo sigue pendiente: Maven solo
verifica una parte del contenido y los ejemplos Java.
