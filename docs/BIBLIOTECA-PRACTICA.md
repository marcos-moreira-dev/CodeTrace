# Biblioteca de práctica integrada (v1)

Biblioteca de transcripción autocontenida. Los **44** archivos de ejemplo son originales y se incorporan al JAR. No se descargan repositorios ni se compila código durante el entrenamiento.

## Packs
- `c`: 2 archivos
- `cmd`: 2 archivos
- `cpp`: 2 archivos
- `csharp`: 2 archivos
- `go`: 2 archivos
- `java21`: 12 archivos
- `javascript`: 2 archivos
- `powershell`: 2 archivos
- `python`: 2 archivos
- `ruby`: 2 archivos
- `shell`: 2 archivos
- `typescript`: 12 archivos

Java 21 y TypeScript abarcan sintaxis, tipos, funciones, estructuras de datos nativas, algoritmos y patrones de diseño. Se tomaron como referencia conceptual las secciones correspondientes de *Java SE 21 Language Specification* y *The Concise TypeScript Book*, así como las categorías del repositorio `marcos-moreira-dev/Estructuras-de-Datos-y-Algoritmos`. **Los ejemplos son implementaciones nuevas y reducidas para mecanografía, no copias literales de los libros o del repositorio.**

Los demás lenguajes contienen dos ejemplos de sintaxis iniciales, ampliables en versiones futuras. Los packs no se presentan como un currículo obligatorio ni asignan calificaciones: el usuario elige libremente qué texto transcribir.

## Infraestructura

- `practice/catalog.tsv`: inventario de recursos empaquetados, un archivo por línea; primera columna ID de pack, segunda ruta relativa.
- `com.mmdev.codetrace.library.BuiltInLibrary`: valida inventario y expone los packs; materializa copias de referencia en `~/.codetrace/practice-library/v1/<pack>/` para reutilizar el mismo editor, árbol, huellas y sistema de sesiones.
- El original del JAR es la única autoridad: en aperturas posteriores se restauran los ficheros que hayan cambiado en la carpeta de caché.
- La reproducción y las estadísticas usan exactamente `TypingSession`; las omisiones Turbo no aportan caracteres correctos.
- La práctica **no ejecuta** los archivos. El módulo de comandos externos, cuando se usa, puede modificar físicamente la carpeta de caché; se recomienda no configurarlo para el material interno.
- La persistencia del avance continúa mediante las sesiones `.ctrace`, por separado de los textos de referencia.

La biblioteca debe continuar 100 % offline. No se depende de una conexión GitHub en tiempo de ejecución.
