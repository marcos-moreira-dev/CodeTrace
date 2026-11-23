// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

/** Ejemplo de consola: ejecutar MainStrategy; salida esperada: Normal: 100.0; Fidelidad: 90.0. */

/** Estrategia intercambiable: puede implementarse con una lambda. */
@FunctionalInterface
public interface Descuento {
    double aplicar(double total);
}
