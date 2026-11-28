// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Descuento.java MainStrategy.java Strategy.java
// Ejecutar: java MainStrategy
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reglas de descuento en la inscripción del torneo.
// Patrón strategy: Intercambiar la política de cálculo sin alterar el cliente.
// Glosario: Torneo: competición de varias partidas entre equipos.
// Este archivo define exactamente un tipo reutilizable, sin main.

/** Ejemplo de consola: ejecutar MainStrategy; salida esperada: Normal: 100.0; Fidelidad: 90.0. */

/** Estrategia intercambiable: puede implementarse con una lambda. */
@FunctionalInterface
public interface Descuento {
    double aplicar(double total);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Descuento.java MainStrategy.java Strategy.java
 *   java MainStrategy
 * Nota: Descuento.java es un componente; la salida corresponde al cliente MainStrategy.java.
 * Salida esperada:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
