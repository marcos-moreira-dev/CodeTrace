// Tema:
// Una cláusula implements no puede enumerar dos veces la misma interfaz. El compilador rechaza
// la declaración redundante.
// Compilar: javac --release 21 -encoding UTF-8 -d . Duplicada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class Duplicada implements java.io.Serializable, java.io.Serializable {}

// Salida esperada:
// Error: repeated interface
