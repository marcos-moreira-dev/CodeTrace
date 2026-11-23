// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" Callbacks
// Salida esperada: Hola, seguido de Lista.
/** Interfaz funcional para recibir mensajes como callback. */
@FunctionalInterface
public interface Receptor {
    void recibir(String mensaje);
}
