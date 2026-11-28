// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainDecorator.java Notificador.java Notificadores.java
// Ejecutar: java codetrace.practica.decorator.MainDecorator
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Avisos que recibe un equipo durante una ronda ficticia.
// Patrón decorator: Añadir formato o información sin cambiar el notificador original.
// Glosario: Ronda: segmento breve de la partida; equipo: conjunto de jugadores.

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
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainDecorator.java Notificador.java Notificadores.java
 *   java codetrace.practica.decorator.MainDecorator
 * Nota: Notificadores.java es un componente; la salida corresponde al cliente MainDecorator.java.
 * Salida esperada:
 *   Enviado: [INFO] Servicio disponible
 *   Envíos acumulados: 1
 *   Enviado: [INFO] Proceso finalizado
 *   Envíos acumulados: 2
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
