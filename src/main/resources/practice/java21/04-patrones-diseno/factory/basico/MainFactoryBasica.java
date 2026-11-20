// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" MainFactoryBasica

/**
 * Cliente de una fábrica simple, distinto de Factory Method GoF.
 *
 * <p>Una fábrica simple concentra la creación de un objeto para que
 * otros consumidores conozcan el contrato Mensaje, pero no construyan
 * la implementación concreta Correo. No es obligatorio llamarla
 * Factory Method: en ese patrón la creación se delega habitualmente
 * a subclases de una abstracción creadora.
 *
 * <p>Factory.java enseña el contrato y la decisión de creación.
 * Aquí solo invocamos y presentamos el resultado, manteniendo el
 * foco en la responsabilidad de cada archivo. La relación queda
 * visible también en el mapa de dependencias de CodeTrace.
 */
public final class MainFactoryBasica {
    public static void main(String[] args) {
        Factory.Mensaje mensaje = Factory.crear("cliente@example.invalid");
        System.out.println(mensaje.enviar());
    }
}
