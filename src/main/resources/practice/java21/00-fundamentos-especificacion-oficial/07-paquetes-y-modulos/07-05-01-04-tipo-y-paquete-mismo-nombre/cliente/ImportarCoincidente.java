// Tema:
// Un segmento de nombre puede identificar un paquete en un contexto y un tipo en otro. Las
// declaraciones import permiten resolver nombres calificados según su posición.
// Compilar: javac --release 21 -encoding UTF-8 -d . Vector/Objeto.java cliente/ImportarCoincidente.java
// Ejecutar: java cliente.ImportarCoincidente
// Comandos desde la carpeta de la unidad.

package cliente;
import java.util.Vector;
import Vector.Objeto;
public class ImportarCoincidente {
    public static void main(String[] args) {
        System.out.println(new Vector<>().getClass().getName());
        System.out.println(new Objeto().getClass().getName());
    }
}

// Salida esperada:
// java.util.Vector
// Vector.Objeto
