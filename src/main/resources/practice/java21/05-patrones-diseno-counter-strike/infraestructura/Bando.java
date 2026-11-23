// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Counter-Strike representa dos bandos en una partida por equipos.
 * CT significa "antiterroristas" y T, "terroristas", dentro del videojuego.
 * Usamos enum porque un jugador no puede cambiar arbitrariamente de bando.
 */
public enum Bando {
    CT("Antiterroristas"), T("Terroristas");

    private final String descripcion;
    Bando(String descripcion) { this.descripcion = descripcion; }
    public String descripcion() { return descripcion; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * Los mensajes CT/T de la consola proceden de las constantes del bando de cada jugador.
 * Salida esperada:
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 *   Marcador: CT 2 - 1 T
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
