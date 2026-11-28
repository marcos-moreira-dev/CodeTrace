// Tema:
// public permite acceso desde otros paquetes; protected y el acceso sin modificador restringen
// otros usos. El cliente usa los métodos accesibles sin acceder directamente a los campos
// restringidos.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java puntos/ListaPuntos.java usuario/Mover.java
// Ejecutar: java usuario.Mover
// Comandos desde la carpeta de la unidad.

package puntos;
public class Punto {
    protected int x, y;
    public void mover(int dx,int dy) { x += dx; y += dy; }
    public int getX() { return x; }
}

// Salida esperada:
// 4
