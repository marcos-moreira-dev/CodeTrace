// Tema:
// Una clase no puede heredar dos parametrizaciones distintas de la misma interfaz genérica,
// porque sus contratos pueden entrar en conflicto después del borrado.
// Compilar: javac --release 21 -encoding UTF-8 -d . Identificable.java Numerico.java Textual.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Numerico implements Identificable<Integer> {}

// Salida esperada:
// Error: cannot be inherited with different arguments
