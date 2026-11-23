// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

package codetrace.practica.patrones.composite;

/** Ejemplo de consola: ejecutar codetrace.practica.patrones.composite.MainComposite; salida esperada: Bytes: 220. */

/** Hoja inmutable; su peso se obtiene del componente del record. */
public record Archivo(String nombre, int bytes) implements Elemento {
    public Archivo {
        if (bytes < 0) throw new IllegalArgumentException("bytes");
    }
}
