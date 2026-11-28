// Tema:
// El compilador exige que el tipo de la expresión sea compatible con el tipo probado por
// instanceof. Una relación imposible entre dos clases puede rechazarse sin ejecutar el
// programa.
// Compilar: javac --release 21 -encoding UTF-8 -d . Estrella.java Mineral.java ComparacionInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Estrella {}

// Salida esperada:
// Error: incompatible types
