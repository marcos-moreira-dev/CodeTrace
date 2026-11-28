// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.2; ejemplo 8.2-3
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Punto.java otro/PuntoExtendido.java
// Ejecutar: java otro.PuntoExtendido
// Comandos desde la carpeta de la unidad.

package base;
public class Punto { public int x; protected int usos; protected static int total; public void mover(int n) { x += n; usos++; total++; } }

// Salida esperada:
// 4 2 2
