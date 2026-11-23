// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Componente didáctico reutilizable: este archivo no tiene main.
// Compilar: en una copia desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

/** Contrato independiente: la fábrica devuelve esta abstracción. */
public interface Mensaje {
    String enviar();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (práctica)
 * Compilar todos los .java desde la carpeta java21 y ejecutar MainFactoryBasica.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * Este tipo no tiene main; su cliente demuestra el comportamiento.
 */
