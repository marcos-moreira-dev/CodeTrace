// Tema:
// Una anotación sin elementos actúa como marcador. Con retención RUNTIME puede consultarse
// mediante reflexión; sin ella, no necesariamente está disponible en ejecución.
// Compilar: javac --release 21 -encoding UTF-8 -d . Experimental.java Marcada.java
// Ejecutar: java Marcada
// Comandos desde la carpeta de la unidad.

@Experimental
public class Marcada {
    public static void main(String[] args) { System.out.println("experimental"); }
}

// Salida esperada:
// experimental
