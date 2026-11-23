// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Comunicaciones del torneo: crear avisos para participantes.
// Patrón factory: Una fábrica devuelve un mensaje sin exponer el tipo concreto.
// Glosario: Torneo: competición entre varios equipos y partidas.
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
 * <p>Mensaje.java enseña el contrato; Correo.java, el record; Factory.java, la creación.
 * Aquí solo invocamos y presentamos el resultado, manteniendo el
 * foco en la responsabilidad de cada archivo. La relación queda
 * visible también en el mapa de dependencias de CodeTrace.
 */
public final class MainFactoryBasica {
    public static void main(String[] args) {
        Mensaje mensaje = Factory.crear("cliente@example.invalid");
        System.out.println(mensaje.enviar());
    }
}

/*
 * Salida esperada de consola:
 *   Correo a cliente@example.invalid
 * El cliente recibe el contrato Mensaje sin construir Correo directamente.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainFactoryBasica
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
