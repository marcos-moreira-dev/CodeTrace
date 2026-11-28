// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.6; ejemplo 6.6-2
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
