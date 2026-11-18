# Versiones y comandos de la biblioteca CodeTrace

Esta matriz fija **versiones de referencia didácticas**. No afirma que sean las
últimas versiones publicadas. Es más importante poder reproducir cada ejercicio.

Cada código incluido en \`src/main/resources/practice/<paquete>\` contiene al
principio un comentario con su versión/estándar, el comando de compilación
o comprobación, y, si existe punto de entrada, el comando de ejecución.

| Paquete | Objetivo compatible | Herramienta | Ejemplo de comando |
|---|---|---|---|
| java21 | Java 21 LTS | Temurin JDK 21 | \`javac -d "$env:TEMP\ct-java" Archivo.java\` |
| kotlin | Kotlin 2.2 sobre JVM 21 | kotlinc 2.2 + JDK 21 | \`kotlinc -jvm-target 21 Archivo.kt -include-runtime -d app.jar\` |
| typescript | TS 5.9, ES2022 | \`tsc\` 5.9.3 | \`tsc --noEmit --target ES2022 Archivo.ts\` |
| javascript | ES2022 | Node.js 24 | \`node --check Archivo.js\` |
| python | Python 3.12 | python | \`python -B Archivo.py\` |
| c | C17 | GCC 13+ | \`gcc -std=c17 Archivo.c -o app.exe\` |
| cpp | C++20 | g++ 13+ | \`g++ -std=c++20 Archivo.cpp -o app.exe\` |
| csharp | C# 12 / .NET 8 | \`dotnet\` SDK 8 | \`dotnet build Proyecto.csproj\` |
| go | Go 1.24 | \`go\` | \`go run archivo.go\` |
| ruby | Ruby 3.3 | \`ruby\` | \`ruby -c archivo.rb\` |
| powershell | PowerShell 7.4 | \`pwsh\` | \`pwsh -NoProfile -File archivo.ps1\` |
| shell | POSIX sh | \`sh\` (WSL/Linux) | \`sh -n archivo.sh\` |
| cmd | Windows 10/11 CMD | \`cmd.exe\` | \`cmd /c archivo.cmd\` |

## Qué significa comprobar y ejecutar

- **Compilar** comprueba que el traductor admite el código y genera un objeto
  o ejecutable. Puede ser suficiente para una clase sin método \`main\`.
- **Comprobar sintaxis/tipos** valida sin producir archivos ejecutables.
  Por ejemplo, \`tsc --noEmit\`, \`node --check\` o \`ruby -c\`.
- **Ejecutar** exige un punto de entrada o sentencias a nivel superior.
  Los ejemplos de estructuras, tipos o patrones pueden ser bibliotecas sin
  \`main\` y no necesitan inventar uno para demostrar que compilan.

Los comandos de cada comentario están orientados a **PowerShell desde la raíz
de una copia del paquete**. Cuando el compilador debe generar archivos, se
sugiere hacerlo bajo \`$env:TEMP\`, no dentro de la referencia original.
Por ejemplo, crear un proyecto C# de prueba en una carpeta temporal.

No se fuerza un único patrón o una misma cantidad de ejercicios a lenguajes
imperativos, orientados a objetos y de shell. La equivalencia es **temática**:
fundamentos, tipos, funciones, estructuras, algoritmos y patrones solamente
cuando el lenguaje los justifica.

## Kotlin y la biblioteca ampliada

La primera ampliación Kotlin contiene **16 ejercicios** equivalentes a
Java/TypeScript: sintaxis, contratos, persona, excepciones, lambdas, callbacks,
lista, pila, cola circular, búsqueda binaria, inserción, Euclides, Factory,
Strategy y Observer. La biblioteca total tiene **108 referencias** en 13 packs: Java, Kotlin y TypeScript (16 cada uno), y diez lenguajes adicionales (seis cada uno). Las ampliaciones no intentan imponer estructuras orientadas a objetos en CMD o shell.

Compilar clases sin \`main\` es una comprobación perfectamente válida. Kotlin
usa \`kotlinc\`; no necesita Gradle ni Android Studio para estos ejemplos.

## Seguridad del proyecto original

La lectura y práctica de CodeTrace no modifica el original. Los comandos
externos que ejecutes desde la consola del proyecto **sí pueden escribir en
su directorio**, porque se ejecutan en tu sistema operativo. Por tanto, prueba
compilación de ejemplos sobre una **copia de trabajo**, no sobre proyectos
de clientes ni carpetas de referencia irreemplazables.
