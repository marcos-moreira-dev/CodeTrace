// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Avisos que recibe un equipo durante una ronda ficticia.
// Patrón decorator: Añadir formato o información sin cambiar el notificador original.
// Glosario: Ronda: segmento breve de la partida; equipo: conjunto de jugadores.
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.decorator.MainDecorator

package codetrace.practica.decorator;

/**
 * Cliente del patrón Decorator: añadir comportamiento mediante composición.
 *
 * <p>Un notificador básico solo entrega un mensaje. Decoradores pueden
 * anteponer una etiqueta o registrar el número de envíos sin modificar
 * su implementación. A diferencia de heredar todas las combinaciones,
 * es posible componer capas dinámicamente, aunque importa su orden.
 *
 * <p>La interfaz funcional de Java permite mezclar clases anónimas
 * y expresiones lambda sin crear una clase por cada regla.
 */
public final class MainDecorator {
    public static void main(String[] args) {
        Notificador emisor = Notificadores.contar(
                Notificadores.prefijar(Notificadores.consola(), "[INFO] "));
        emisor.enviar("Servicio disponible");
        emisor.enviar("Proceso finalizado");
    }
}

/*
 * Salida esperada de consola:
 *   Enviado: [INFO] Servicio disponible
 *   Envíos acumulados: 1
 *   Enviado: [INFO] Proceso finalizado
 *   Envíos acumulados: 2
 * El decorador de contador imprime después de delegar al notificador.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.decorator.MainDecorator
 * Salida esperada:
 *   Enviado: [INFO] Servicio disponible
 *   Envíos acumulados: 1
 *   Enviado: [INFO] Proceso finalizado
 *   Envíos acumulados: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
