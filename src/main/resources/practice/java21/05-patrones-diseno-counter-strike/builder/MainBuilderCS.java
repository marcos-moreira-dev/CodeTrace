// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.builder.MainBuilderCS
// Dominio: Counter-Strike — el anfitrión configura una sala privada de Counter-Strike por pasos.
// Patrón: Builder; ejercicio independiente, no oficial.
// Glosario: Sala = partida personalizada; mapa = escenario virtual; rehén = personaje virtual que puede ser objetivo de rescate.
package codetrace.practica.counterstrike.builder;

/** Cliente: crea una configuración legible sin un constructor de muchos datos. */
public final class MainBuilderCS {
    public static void main(String[] args) {
        // cs_office es un nombre de mapa del juego; no se carga ningún recurso real.
        ConfiguracionSala sala = new ConfiguradorSala()
                .mapa("cs_office")
                .rondas(12)
                .rescateRehenes(true)
                .construir();

        // Un record ofrece accesores con el nombre exacto de cada componente.
        System.out.println("Sala: " + sala.mapa() + ", rondas=" + sala.rondas()
                + ", rehenes=" + sala.rescateRehenes());
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.builder.MainBuilderCS
 * Nota: MainBuilderCS.java se estudia junto al cliente MainBuilderCS.java.
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
