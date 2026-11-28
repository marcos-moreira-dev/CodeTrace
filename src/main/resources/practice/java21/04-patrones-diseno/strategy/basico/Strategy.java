// Compilar (desde esta carpeta): javac -encoding UTF-8 Descuento.java MainStrategy.java Strategy.java
// Ejecutar: java MainStrategy
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reglas de descuento en la inscripción del torneo.
// Patrón strategy: Intercambiar la política de cálculo sin alterar el cliente.
// Glosario: Torneo: competición de varias partidas entre equipos.

/**
 * Contexto del patrón Strategy: delega una regla de negocio.
 *
 * Un @FunctionalInterface con un único método abstracto admite
 * expresiones lambda y referencias a métodos de Java 21.
 */
public final class Strategy {
    public static double cobrar(double total, Descuento estrategia) {
        if (total < 0) throw new IllegalArgumentException("Subtotal");
        return estrategia.aplicar(total);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Descuento.java MainStrategy.java Strategy.java
 *   java MainStrategy
 * Nota: Strategy.java es un componente; la salida corresponde al cliente MainStrategy.java.
 * Salida esperada:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
