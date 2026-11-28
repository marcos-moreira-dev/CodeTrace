// Compilar (desde esta carpeta): javac -encoding UTF-8 ArchivosYNio.java
// Ejecutar: java ArchivosYNio

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Cliente de NIO.2, rutas y archivos temporales.
 *
 * <p>Una Path describe una ubicación; Files ofrece operaciones sobre el
 * sistema de archivos. La ruta temporal se crea fuera del proyecto para
 * que el ejemplo nunca modifique la referencia de CodeTrace. Se utiliza
 * try/finally para eliminar el archivo, también si falla la lectura.
 */
public class ArchivosYNio {
    public static void main(String[] args) throws Exception {
        Path archivo = Files.createTempFile("codetrace-estudio-", ".txt");
        try {
            Files.writeString(archivo, "Hola desde NIO.2", StandardCharsets.UTF_8);
            System.out.println(Files.readString(archivo, StandardCharsets.UTF_8));
            System.out.println("Nombre: " + archivo.getFileName());
        } finally {
            Files.deleteIfExists(archivo);
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ArchivosYNio.java
 *   java ArchivosYNio
 * Salida esperada:
 *   Hola desde NIO.2
 *   Nombre: codetrace-estudio-<identificador variable>.txt
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
