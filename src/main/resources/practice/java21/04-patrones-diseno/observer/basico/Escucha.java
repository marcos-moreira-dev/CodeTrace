// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

/** Ejemplo de consola: ejecutar MainObservadorBasico; salida esperada: consola=42 log=42. */

/** Contrato de notificación del observador básico. */
@FunctionalInterface
public interface Escucha {
    void alCambiar(int nuevoValor);
}
