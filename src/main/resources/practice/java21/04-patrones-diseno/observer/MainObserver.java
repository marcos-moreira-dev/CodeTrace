// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Marcador de ronda que notifica resultados a los espectadores.
// Patrón observer: Varios observadores reciben cambios sin ser consultados continuamente.
// Glosario: Ronda: segmento breve de una partida; marcador: registro de resultados.
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.observer.MainObserver
package codetrace.observer;
/**
 * Cliente Observer: CentroEventos publica sin conocer los observadores
 * concretos. La lambda implementa una interfaz funcional que recibe
 * Evento; no se requiere una clase por cada suscriptor.
 *
 * Separa notificación del dominio frente a las reacciones del cliente.
 */
public final class MainObserver {
    public static void main(String[] args) {
        CentroEventos centro = new CentroEventos();
        centro.registrar(evento -> System.out.println(evento.mensaje()));
        centro.publicar(new Evento("Nueva venta"));
    }
}

/*
 * Salida esperada de consola:
 *   Nueva venta
 * La lambda suscrita recibe el Evento y presenta su mensaje.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.observer.MainObserver
 * Salida esperada:
 *   Nueva venta
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
