# Referencias y comandos para los 14 lenguajes

Las versiones son objetivos didácticos reproducibles, no afirmaciones sobre lo último publicado. **Todos los archivos de la biblioteca** muestran en sus primeros comentarios versión y comando sugerido; compilar o ejecutar es una operación voluntaria, fuera del modo de práctica.

| Paquete | Versión didáctica | Comando de comprobación |
| --- | --- | --- |
| `java21` | Java 21 LTS | `javac --release 21` |
| `kotlin` | Kotlin 2.2 / JVM 21 | `kotlinc -jvm-target 21` |
| `typescript` | TypeScript 5.9 / ES2022 | `tsc --noEmit --target ES2022` |
| `javascript` | ES2022 / Node.js 24 | `node --check` |
| `python` | Python 3.12 | `python -B` |
| `c` | C17 / GCC 13+ | `gcc -std=c17` |
| `cpp` | C++20 / g++ 13+ | `g++ -std=c++20` |
| `csharp` | C# 12 / .NET 8 | `dotnet build` |
| `go` | Go 1.24 | `go run` |
| `ruby` | Ruby 3.3 | `ruby -c` |
| `powershell` | PowerShell 7.4 | `pwsh -NoProfile -File` |
| `shell` | POSIX sh (Linux/WSL) | `sh -n` |
| `cmd` | Windows CMD | `cmd /c` |
| `nasm-x64` | NASM x86-64 ELF64 / Linux-WSL | `nasm -f elf64` + `ld` |

Diferenciar **compilar**, **comprobar** y **ejecutar**. Clases sin `main`, headers C/C++ y módulos auxiliares no están obligados a ejecutar individualmente; se compilan junto al cliente. Un ejercicio multiarchivo requiere incluir todas sus dependencias en el comando. `node --check` solo analiza sintaxis, `tsc --noEmit` valida tipos sin emitir JS, `ruby -c` revisa sintaxis.

Para .NET 8 se puede usar un proyecto temporal creado con `dotnet new console`, copiando el único cliente para compilar o ejecutar. Los fuentes ELF64 no son ejecutables PE de Windows. Las rutas bajo `$env:TEMP` son temporales y no comprometen la referencia.

Se conservan ejemplos existentes siempre que puedan verificarse; al mejorar una cabecera actualizar la ruta y el contrato. Los comandos no se ejecutan automáticamente desde CodeTrace.


## Convención de rutas v4

Los comandos referenciales se leen desde la raíz del paquete de lenguaje y deben apuntar al archivo dentro de su **carpeta de unidad**. Para proyectos multiarchivo, compilar todas las fuentes de la unidad. La estructura de carpetas no modifica los estándares de compilación: Java 21, C17, C++20, TypeScript 5.9, etc. Las referencias son material de estudio; CodeTrace no ejecuta los comandos automáticamente.
