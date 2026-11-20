// CodeTrace | Referencia: Java 21 LTS / JDK 21
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
