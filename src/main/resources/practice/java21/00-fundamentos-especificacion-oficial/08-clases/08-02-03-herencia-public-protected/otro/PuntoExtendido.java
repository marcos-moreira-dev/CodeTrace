// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.2; ejemplo 8.2-3
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Punto.java otro/PuntoExtendido.java
// Ejecutar: java otro.PuntoExtendido
// Comandos desde la carpeta de la unidad.

package otro;
public class PuntoExtendido extends base.Punto {
    void retroceder(int n) { x -= n; usos++; total++; }
    public static void main(String[] args) {
        PuntoExtendido p = new PuntoExtendido();
        p.mover(6); p.retroceder(2);
        System.out.println(p.x + " " + p.usos + " " + total);
    }
}

// Salida esperada:
// 4 2 2
