// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" Callbacks
// Salida esperada: Hola, seguido de Lista.
/** Interfaz funcional para recibir mensajes como callback. */
@FunctionalInterface
public interface Receptor {
    void recibir(String mensaje);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out Callbacks
 * Nota: Receptor.java es un componente; la salida corresponde al cliente Callbacks.java.
 * Salida esperada:
 *   Hola
 *   Lista
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
