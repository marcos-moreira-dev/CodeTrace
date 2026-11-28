// Tema:
// Una anotación sin elementos actúa como marcador. Con retención RUNTIME puede consultarse
// mediante reflexión; sin ella, no necesariamente está disponible en ejecución.
// Compilar: javac --release 21 -encoding UTF-8 -d . Preliminar.java AnotacionMarca.java
// Ejecutar: java AnotacionMarca
// Comandos desde la carpeta de la unidad.

@Preliminar
public class AnotacionMarca {
    public static void main(String[] args) { System.out.println(AnotacionMarca.class.isAnnotationPresent(Preliminar.class)); }
}

// Salida esperada:
// true
