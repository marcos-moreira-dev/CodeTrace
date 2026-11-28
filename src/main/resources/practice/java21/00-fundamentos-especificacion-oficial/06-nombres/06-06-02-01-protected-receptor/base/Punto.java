// Tema:
// Fuera del paquete, una subclase puede acceder al miembro protected heredado mediante una
// referencia cuyo tipo sea esa subclase o un subtipo suyo, no mediante cualquier referencia a
// la superclase.
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Punto.java otro/Punto3D.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package base;
public class Punto { protected int x; }

// Salida esperada:
// Error: has protected access
