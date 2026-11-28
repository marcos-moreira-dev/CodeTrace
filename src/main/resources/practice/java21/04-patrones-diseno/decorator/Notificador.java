// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainDecorator.java Notificador.java Notificadores.java
// Ejecutar: java codetrace.practica.decorator.MainDecorator
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Avisos que recibe un equipo durante una ronda ficticia.
// Patrón decorator: Añadir formato o información sin cambiar el notificador original.
// Glosario: Ronda: segmento breve de la partida; equipo: conjunto de jugadores.

package codetrace.practica.decorator;

/** Contrato básico para entregar una notificación. */
@FunctionalInterface
public interface Notificador {
    void enviar(String mensaje);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainDecorator.java Notificador.java Notificadores.java
 *   java codetrace.practica.decorator.MainDecorator
 * Nota: Notificador.java es un componente; la salida corresponde al cliente MainDecorator.java.
 * Salida esperada:
 *   Enviado: [INFO] Servicio disponible
 *   Envíos acumulados: 1
 *   Enviado: [INFO] Proceso finalizado
 *   Envíos acumulados: 2
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
