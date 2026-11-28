// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.2; ejemplo 8.2-4
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java Punto3D.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Punto3D extends Punto { void mover() { super.mover(); total++; } }

// Salida esperada:
// Error: has private access
