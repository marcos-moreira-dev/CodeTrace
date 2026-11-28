// Tema:
// Un tipo miembro puede identificarse usando el nombre de su tipo exterior y, si corresponde,
// su paquete. Un miembro static no requiere una instancia exterior.
// Compilar: javac --release 21 -encoding UTF-8 -d . tipos/Exterior.java TiposCalificados.java
// Ejecutar: java TiposCalificados
// Comandos desde la carpeta de la unidad.

package tipos;
public class Exterior {
    public static class Interior {}
}

// Salida esperada:
// Interior
