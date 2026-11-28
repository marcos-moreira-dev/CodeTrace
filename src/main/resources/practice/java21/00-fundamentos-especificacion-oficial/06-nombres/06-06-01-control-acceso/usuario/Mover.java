// Tema:
// public permite acceso desde otros paquetes; protected y el acceso sin modificador restringen
// otros usos. El cliente usa los métodos accesibles sin acceder directamente a los campos
// restringidos.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java puntos/ListaPuntos.java usuario/Mover.java
// Ejecutar: java usuario.Mover
// Comandos desde la carpeta de la unidad.

package usuario;
public class Mover {
    public static void main(String[] args) {
        puntos.Punto p = new puntos.Punto();
        p.mover(4, 5);
        System.out.println(p.getX());
    }
}

// Salida esperada:
// 4
