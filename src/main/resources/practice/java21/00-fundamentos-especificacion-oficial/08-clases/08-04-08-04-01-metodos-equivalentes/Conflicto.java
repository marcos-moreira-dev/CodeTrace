// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.4; ejemplo 8.4.8.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Generica.java Conflicto.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Conflicto extends Generica<String> {}

// Salida esperada:
// Error: same signature
