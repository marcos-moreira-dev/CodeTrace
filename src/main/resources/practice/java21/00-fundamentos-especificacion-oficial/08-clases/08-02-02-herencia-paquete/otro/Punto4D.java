// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.2; ejemplo 8.2-2
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java puntos/Punto3D.java otro/Punto4D.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package otro;
class Punto4D extends puntos.Punto3D { void mover() { x++; z++; } }

// Salida esperada:
// Error: is not public
