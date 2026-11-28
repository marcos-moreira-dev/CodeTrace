// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.4; ejemplo 8.1.4-3
// Compilar: javac --release 21 -encoding UTF-8 -d . CircularA.java CircularB.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class CircularB extends CircularA {}

// Salida esperada:
// Error: cyclic inheritance
