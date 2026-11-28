// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.6; ejemplo 6.6-4
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Punto.java otro/Derivado.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package base;
public class Punto { void mover(int n) {} public void moverTambien(int n) { mover(n); } }

// Salida esperada:
// Error: cannot be accessed from outside package
