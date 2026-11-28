// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 Descuento.java MainStrategy.java Strategy.java
// Ejecutar: java MainStrategy
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reglas de descuento en la inscripción del torneo.
// Patrón strategy: Intercambiar la política de cálculo sin alterar el cliente.
// Glosario: Torneo: competición de varias partidas entre equipos.

/**
 * Cliente Strategy: reglas intercambiables en un mismo contexto.
 *
 * El cliente configura dos lambdas sobre la interfaz Descuento.
 * Strategy.cobrar() no pregunta cuál es la clase concreta: ejecuta
 * el contrato. Se evita forzar subclases cuando la política es
 * una función sin estado, pero una clase sería válida para estrategias
 * configurables y con responsabilidad propia.
 */
public final class MainStrategy {
    public static void main(String[] args) {
        Descuento normal = importe -> importe;
        Descuento fidelidad = importe -> importe * .90;
        double subtotal = 100;
        double esperado = Strategy.cobrar(subtotal, fidelidad);
        if (esperado != 90) throw new AssertionError("Descuento incorrecto");
        System.out.println("Normal: " + Strategy.cobrar(subtotal, normal));
        System.out.println("Fidelidad: " + esperado);
    }
}

/*
 * Salida esperada de consola:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 * Se cambia la política de cobro sin modificar el contexto Strategy.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Descuento.java MainStrategy.java Strategy.java
 *   java MainStrategy
 * Salida esperada:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
