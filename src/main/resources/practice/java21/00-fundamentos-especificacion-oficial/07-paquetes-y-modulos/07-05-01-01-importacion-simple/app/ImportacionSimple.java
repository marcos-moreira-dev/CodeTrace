// Tema:
// import permite usar el nombre simple de un tipo accesible declarado en otro paquete. No
// copia el código ni importa automáticamente todos los miembros del tipo.
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
