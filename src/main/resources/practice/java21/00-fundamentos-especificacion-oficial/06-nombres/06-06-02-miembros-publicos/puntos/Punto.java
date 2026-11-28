// Tema:
// Una clase y sus miembros public son accesibles desde otros paquetes cuando el tipo es
// accesible. Los detalles sin modificador siguen restringidos al paquete.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java usuario/Contar.java
// Ejecutar: java usuario.Contar
// Comandos desde la carpeta de la unidad.

package puntos;
public class Punto {
    int x, y;
    public static int movimientos;
    public Punto() {}
    public void mover(int dx, int dy) { x += dx; y += dy; movimientos++; }
}

// Salida esperada:
// 1
