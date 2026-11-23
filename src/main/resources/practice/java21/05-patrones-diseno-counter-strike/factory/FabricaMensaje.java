// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.factory.MainFactoryCS
// Dominio: Counter-Strike — chat de equipo: generar un aviso sin construir su implementación en el cliente.
// Patrón: Simple Factory; ejercicio educativo no oficial.
// Glosario: Chat = canal de mensajes de la partida; ronda = etapa breve del encuentro.
package codetrace.practica.counterstrike.factory;

/** Fábrica simple: decide qué objeto concreto se devuelve. */
public final class FabricaMensaje {
    private FabricaMensaje() {}
    public static MensajeSala crearAvisoDeRonda(String texto) {
        // El cliente recibe MensajeSala; no necesita crear MensajeChat.
        return new MensajeChat(texto);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.factory.MainFactoryCS
 * Nota: FabricaMensaje.java pertenece al ejercicio; el cliente es MainFactoryCS.java.
 * Salida esperada:
 *   Chat de equipo: Ronda iniciada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
