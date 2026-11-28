// Tema:
// Una subclase de otro paquete puede usar los miembros public y los protected accesibles según
// las reglas de herencia. La visibilidad no exige que todos los campos sean public.
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
