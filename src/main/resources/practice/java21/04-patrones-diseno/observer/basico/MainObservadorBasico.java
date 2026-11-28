// Compilar (desde esta carpeta): javac -encoding UTF-8 Escucha.java MainObservadorBasico.java Observador.java
// Ejecutar: java MainObservadorBasico
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Marcador de ronda que notifica resultados a los espectadores.
// Patrón observer: Varios observadores reciben cambios sin ser consultados continuamente.
// Glosario: Ronda: segmento breve de una partida; marcador: registro de resultados.

/**
 * Cliente Observer básico con callbacks y lambdas de Java 21.
 *
 * <p>Un emisor puede notificar a múltiples suscriptores cuando
 * cambia su valor, sin conocer lo que esos suscriptores hacen.
 * Observador.java contiene la lista de callbacks y el mecanismo
 * de publicación; este cliente crea oyentes y provoca cambios.
 *
 * <p>Esta versión sencilla no incluye desuscripción ni políticas
 * ante excepciones de observadores. En un sistema productivo esos
 * contratos importan y el ciclo de vida de los suscriptores debe
 * definirse para evitar referencias retenidas.
 */
public final class MainObservadorBasico {
    public static void main(String[] args) {
        Observador sensor = new Observador();
        StringBuilder eventos = new StringBuilder();
        sensor.suscribir(v -> eventos.append("consola=").append(v).append(" "));
        sensor.suscribir(v -> eventos.append("log=").append(v));
        sensor.actualizar(42);
        System.out.println(eventos);
        if (sensor.valor() != 42 || !eventos.toString().contains("log=42"))
            throw new AssertionError("Fallo de notificación");
    }
}

/*
 * Salida esperada de consola:
 *   consola=42 log=42
 * Dos suscriptores reciben el mismo valor mediante callbacks.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Escucha.java MainObservadorBasico.java Observador.java
 *   java MainObservadorBasico
 * Salida esperada:
 *   consola=42 log=42
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
