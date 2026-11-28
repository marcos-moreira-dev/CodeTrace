// Compilar (desde la raíz de 05-patrones-diseno-counter-strike): javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Elemento del inventario de un jugador ficticio de Counter-Strike.
 *
 * "MP9" es el nombre de un subfusil del videojuego. Aquí es solamente
 * un objeto con nombre, categoría, coste en créditos y un contador
 * imaginario de capacidad. No se modela el uso de armas reales.
 *
 * @param nombre etiqueta visible en el inventario
 * @param categoria familia del objeto, p. ej. "subfusil"
 * @param precio coste en créditos del videojuego, no en dólares
 * @param capacidadVirtual contador ilustrativo de la interfaz del juego
 */
public record ObjetoVirtual(String nombre, String categoria,
                            int precio, int capacidadVirtual) {
    public ObjetoVirtual {
        // La tienda no admite fichas sin nombre/categoría ni costes
        // negativos: comprar una ficha no debe aumentar los créditos.
        if (nombre == null || nombre.isBlank()
                || categoria == null || categoria.isBlank()
                || precio < 0 || capacidadVirtual < 0)
            throw new IllegalArgumentException("Ficha de inventario inválida");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   [TIENDA] Astra añadió MP9 | créditos=950 | capacidad ilustrativa=30
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
