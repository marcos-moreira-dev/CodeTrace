// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Describe un escenario virtual, no un archivo ni una imagen del juego.
 * cs_office es un mapa conocido del modo rescate de Counter-Strike.
 * Sus zonas son etiquetas narrativas inventadas para este ejercicio.
 *
 * duracionRondaSegundos es una regla simplificada de nuestro simulador,
 * NO una afirmación de que todas las versiones del juego usen ese tiempo.
 */
public record MapaPartida(String codigo, String nombre,
                          String zonaRehenes, String zonaSegura,
                          int duracionRondaSegundos) {
    public MapaPartida {
        if (codigo == null || codigo.isBlank()
                || nombre == null || nombre.isBlank()
                || zonaRehenes == null || zonaRehenes.isBlank()
                || zonaSegura == null || zonaSegura.isBlank()
                || duracionRondaSegundos <= 0)
            throw new IllegalArgumentException("Mapa inválido");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * El reloj empieza en 115 segundos por ronda según nuestro escenario didáctico.
 * Salida esperada:
 *   Mapa: cs_office | Rescate de rehenes | Rondas: 3
 *   --- RONDA 1 | Reloj: 115 s ---
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
