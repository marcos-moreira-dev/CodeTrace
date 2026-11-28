// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Escucha.java MainObservadorBasico.java Observador.java
// Ejecutar: java MainObservadorBasico
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Marcador de ronda que notifica resultados a los espectadores.
// Patrón observer: Varios observadores reciben cambios sin ser consultados continuamente.
// Glosario: Ronda: segmento breve de una partida; marcador: registro de resultados.
// Este archivo define exactamente un tipo reutilizable, sin main.

/** Ejemplo de consola: ejecutar MainObservadorBasico; salida esperada: consola=42 log=42. */

/** Contrato de notificación del observador básico. */
@FunctionalInterface
public interface Escucha {
    void alCambiar(int nuevoValor);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Escucha.java MainObservadorBasico.java Observador.java
 *   java MainObservadorBasico
 * Nota: Escucha.java es un componente; la salida corresponde al cliente MainObservadorBasico.java.
 * Salida esperada:
 *   consola=42 log=42
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
