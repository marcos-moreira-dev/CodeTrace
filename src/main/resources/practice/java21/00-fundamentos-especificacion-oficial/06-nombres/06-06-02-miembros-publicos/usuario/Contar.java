// Tema:
// Una clase y sus miembros public son accesibles desde otros paquetes cuando el tipo es
// accesible. Los detalles sin modificador siguen restringidos al paquete.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java usuario/Contar.java
// Ejecutar: java usuario.Contar
// Comandos desde la carpeta de la unidad.

package usuario;
public class Contar {
    public static void main(String[] args) {
        puntos.Punto p = new puntos.Punto();
        p.mover(2, 3);
        System.out.println(puntos.Punto.movimientos);
    }
}

// Salida esperada:
// 1
