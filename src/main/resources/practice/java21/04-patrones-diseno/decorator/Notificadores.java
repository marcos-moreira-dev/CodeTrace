// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Avisos que recibe un equipo durante una ronda ficticia.
// Patrón decorator: Añadir formato o información sin cambiar el notificador original.
// Glosario: Ronda: segmento breve de la partida; equipo: conjunto de jugadores.
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: ejecutar desde el archivo cliente del mismo ejercicio

package codetrace.practica.decorator;

import java.util.Objects;

/**
 * Decoradores que envuelven un contrato sin cambiarlo.
 * Cada capa delega y agrega comportamiento antes o después del envío.
 */
public final class Notificadores {
    private Notificadores() {}

    public static Notificador consola() {
        return mensaje -> System.out.println("Enviado: " + mensaje);
    }

    public static Notificador prefijar(Notificador interno, String prefijo) {
        Objects.requireNonNull(interno);
        Objects.requireNonNull(prefijo);
        return mensaje -> interno.enviar(prefijo + mensaje);
    }

    public static Notificador contar(Notificador interno) {
        Objects.requireNonNull(interno);
        return new Notificador() {
            private int enviados;
            @Override public void enviar(String mensaje) {
                interno.enviar(mensaje);
                System.out.println("Envíos acumulados: " + (++enviados));
            }
        };
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.decorator.MainDecorator
 * Nota: Notificadores.java es un componente; la salida corresponde al cliente MainDecorator.java.
 * Salida esperada:
 *   Enviado: [INFO] Servicio disponible
 *   Envíos acumulados: 1
 *   Enviado: [INFO] Proceso finalizado
 *   Envíos acumulados: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
