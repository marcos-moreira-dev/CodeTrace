// Tema:
// Dos métodos cuyos parámetros genéricos difieren pueden tener la misma firma borrada sin
// ser subsignaturas entre sí. Una interfaz no puede heredar ambos contratos incompatibles.
// Compilar: javac --release 21 -encoding UTF-8 -d . Textos.java Numeros.java BorradoInterfaz.java
// ERROR INTENCIONAL de compilación. No ejecutar.

interface BorradoInterfaz extends Textos, Numeros {}

// Salida esperada:
// Error: name clash
