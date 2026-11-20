// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" ArchivosYNio

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
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out ArchivosYNio
 * Salida esperada:
 *   Hola desde NIO.2
 *   Nombre: codetrace-estudio-16205165830369107076.txt
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
