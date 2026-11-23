// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Estado de un personaje no jugable (NPC) en el modo rescate:
 * RETENIDO: está todavía en el escenario sin escolta asignada.
 * ESCOLTADO: un personaje CT está acompañándolo hacia la zona segura.
 * RESCATADO: alcanzó el 100 % del trayecto virtual de la simulación.
 *
 * Es un modelo narrativo sencillo, sin animaciones ni rutas de IA.
 */
public enum EstadoRehen { RETENIDO, ESCOLTADO, RESCATADO }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * La ficha del NPC muestra el paso a RESCATADO cuando su progreso llega a 100 %.
 * Salida esperada:
 *   [REHÉN] Elena | ubicación=Archivo de oficinas | calma=50/100 | integridad=92/100 | escolta=Astra | progreso=100% | estado=RESCATADO
 *   [REHÉN] Omar | ubicación=Sala de juntas | calma=36/100 | integridad=86/100 | escolta=Astra | progreso=100% | estado=RESCATADO
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
