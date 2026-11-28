// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . CentroEventos.java Evento.java MainObserver.java Observador.java
// Ejecutar: java codetrace.observer.MainObserver
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Marcador de ronda que notifica resultados a los espectadores.
// Patrón observer: Varios observadores reciben cambios sin ser consultados continuamente.
// Glosario: Ronda: segmento breve de una partida; marcador: registro de resultados.
package codetrace.observer;
public interface Observador {
    void actualizar(Evento evento);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . CentroEventos.java Evento.java MainObserver.java Observador.java
 *   java codetrace.observer.MainObserver
 * Nota: Observador.java es un componente; la salida corresponde al cliente MainObserver.java.
 * Salida esperada:
 *   Nueva venta
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
