// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §7.5.1; ejemplo 7.5.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . app/ImportacionSimple.java datos/Mensaje.java
// Ejecutar: java app.ImportacionSimple
// Comandos desde la carpeta de la unidad.

package app;
import datos.Mensaje;
public class ImportacionSimple {
    public static void main(String[] args) { System.out.println(Mensaje.TEXTO); }
}

// Salida esperada:
// hola
