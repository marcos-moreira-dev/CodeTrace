// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §7.5.1; ejemplo 7.5.1-4
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
