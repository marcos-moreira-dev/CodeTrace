// Tema:
// La relación de superclase debe ser acíclica. Dos clases no pueden extenderse directa o
// indirectamente entre sí.
// Compilar: javac --release 21 -encoding UTF-8 -d . CircularA.java CircularB.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class CircularA extends CircularB {}

// Salida esperada:
// Error: cyclic inheritance
