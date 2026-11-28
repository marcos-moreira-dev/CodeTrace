// Tema:
// Una subclase de otro paquete puede usar los miembros public y los protected accesibles según
// las reglas de herencia. La visibilidad no exige que todos los campos sean public.
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Punto.java otro/PuntoExtendido.java
// Ejecutar: java otro.PuntoExtendido
// Comandos desde la carpeta de la unidad.

package base;
public class Punto { public int x; protected int usos; protected static int total; public void mover(int n) { x += n; usos++; total++; } }

// Salida esperada:
// 4 2 2
