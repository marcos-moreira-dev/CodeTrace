// Tema:
// El parámetro Iterable<String> describe una colección de textos. El borrado de tipos
// convierte el tipo del parámetro en Iterable.
// Compilar: javac --release 21 -encoding UTF-8 -d . Textos.java Numeros.java BorradoInterfaz.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Textos { int medir(Iterable<String> datos); }

// Salida esperada:
// La compilación conjunta se rechaza: name clash
