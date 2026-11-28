// Tema:
// Dos métodos cuyos parámetros genéricos difieren pueden tener la misma firma borrada sin
// ser subsignaturas entre sí. Una interfaz no puede heredar ambos contratos incompatibles.
// Compilar: javac --release 21 -encoding UTF-8 -d . BorradoInterfaz.java
// ERROR INTENCIONAL de compilación. No ejecutar.

interface Textos { int medir(Iterable<String> datos); }
interface Numeros { int medir(Iterable<Integer> datos); }
interface BorradoInterfaz extends Textos, Numeros {}

// Salida esperada:
// Error: name clash
