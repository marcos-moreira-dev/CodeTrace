// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, inicio de una sesión automática de rescate.
// Patrón: Facade (Fachada); interfaz sencilla ante el subsistema de partida.
// Glosario: NPC = personaje no jugable; HUD = información de pantalla.
package codetrace.practica.counterstrike.facade;

import codetrace.practica.counterstrike.infraestructura.ControladorPartida;
import codetrace.practica.counterstrike.infraestructura.EstadoPartida;
import codetrace.practica.counterstrike.infraestructura.VistaConsola;

/**
 * Fachada del simulador: el cliente pide jugar una partida de Counter-Strike
 * sin tener que ensamblar por separado los subsistemas del modelo y la vista.
 *
 * Facade no reemplaza MVC ni decide quién gana. Su responsabilidad consiste
 * en ofrecer una operación de alto nivel para iniciar una sesión completa.
 * MainCounterStrike es el cliente real de este patrón.
 */
public final class FachadaCounterStrike {
    public EstadoPartida jugarPartida(long semilla) {
        // Cada llamada abre una sesión nueva: la vista relatará los hechos
        // y el controlador creará los equipos, rehenes y marcador en memoria.
        // Así no se mezclan los créditos ni rescates de partidas anteriores.
        VistaConsola vista = new VistaConsola();

        // El controlador recibe la vista y una semilla para que los
        // desenlaces de las rondas sean reproducibles al probar el código.
        ControladorPartida controlador = new ControladorPartida(vista, semilla);

        // Delegamos el ciclo completo al controlador. Al terminar devolvemos
        // el modelo final por si otro cliente quiere consultar el marcador
        // o el historial, sin tener que interpretar las líneas de consola.
        return controlador.simular();
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * Main es el cliente de Facade; extracto de la narración con semilla 42.
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   [CADENA] Rescate: Elena llegó a Zona de extracción
 *   Marcador: CT 1 - 0 T
 *   Marcador: CT 1 - 1 T
 *   [CADENA] Rescate: Omar llegó a Zona de extracción
 *   Marcador: CT 2 - 1 T
 *   Resultado: Gana CT
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
