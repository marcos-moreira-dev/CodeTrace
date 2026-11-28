// Tema:
// Iterable<Integer> describe una colección de enteros. Tras el borrado, medir tiene el
// mismo parámetro Iterable que la otra interfaz, aunque sus tipos genéricos no coinciden.
// Compilar: javac --release 21 -encoding UTF-8 -d . Textos.java Numeros.java BorradoInterfaz.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Numeros { int medir(Iterable<Integer> datos); }

// Salida esperada:
// La compilación conjunta se rechaza: name clash
