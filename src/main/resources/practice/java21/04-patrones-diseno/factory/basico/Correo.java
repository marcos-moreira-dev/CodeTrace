// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Correo de avisos de una competición ficticia por equipos.
// Patrón factory: Crear mensajes mediante un contrato en lugar de acoplar el cliente.
// Glosario: Torneo: competición que agrupa varias partidas.
// Componente didáctico reutilizable: este archivo no tiene main.
// Compilar: en una copia desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

/** Implementación concreta de Mensaje mediante un record de Java 21. */
public record Correo(String destino) implements Mensaje {
    @Override public String enviar() {
        return "Correo a " + destino;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (práctica)
 * Compilar todos los .java desde la carpeta java21 y ejecutar MainFactoryBasica.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * Este tipo no tiene main; su cliente demuestra el comportamiento.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainFactoryBasica
 * Nota: Correo.java es un componente; la salida corresponde al cliente MainFactoryBasica.java.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
