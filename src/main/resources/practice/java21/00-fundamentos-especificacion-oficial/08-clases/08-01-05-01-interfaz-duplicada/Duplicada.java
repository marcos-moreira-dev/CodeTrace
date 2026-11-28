// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.5; ejemplo 8.1.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Duplicada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class Duplicada implements java.io.Serializable, java.io.Serializable {}

// Salida esperada:
// Error: repeated interface
