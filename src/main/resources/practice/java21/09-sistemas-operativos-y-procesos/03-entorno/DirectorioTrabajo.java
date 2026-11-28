// Tema:
// Las rutas relativas se interpretan desde el directorio de trabajo del proceso. No es necesariamente la carpeta de los archivos fuente.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 DirectorioTrabajo.java
// Ejecutar: java DirectorioTrabajo

import java.nio.file.Path;

public class DirectorioTrabajo {
    public static void main(String[] args) throws Exception {
        Path base = Path.of("").toAbsolutePath();
        Path archivo = Path.of("ventas.txt").toAbsolutePath();
        System.out.println("Mismo padre: " + base.equals(archivo.getParent()));
    }
}

// Salida esperada:
// Mismo padre: true
