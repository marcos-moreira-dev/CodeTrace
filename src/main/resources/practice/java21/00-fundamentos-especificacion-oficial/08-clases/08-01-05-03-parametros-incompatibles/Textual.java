// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.5; ejemplo 8.1.5-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Identificable.java Numerico.java Textual.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Textual extends Numerico implements Identificable<String> {}

// Salida esperada:
// Error: cannot be inherited with different arguments
