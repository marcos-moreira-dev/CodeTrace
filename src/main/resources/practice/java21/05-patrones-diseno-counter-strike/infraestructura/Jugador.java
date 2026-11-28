// CodeTrace | Referencia: Java 21 / JDK 21
// Desde la raíz de 05-patrones-diseno-counter-strike.
// Compilar: javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
// Ejecutar: java codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Modelo de un jugador de Counter-Strike durante una partida ficticia.
 * Además del alias y su bando, guarda puntos de vida (PV), armadura
 * ilustrativa, créditos del videojuego e inventario.
 *
 * El inventario se expone como una copia inmodificable para que otra clase
 * no pueda alterar créditos o añadir objetos saltándose comprar().
 */
public final class Jugador {
    private final String alias;
    private final Bando bando;
    // Una lista mutable representa las fichas de equipamiento adquirido
    // durante la partida. Se modifica a través de comprar(), no del HUD.
    private final List<ObjetoVirtual> inventario = new ArrayList<>();
    private int vida = 100;
    private int armadura = 35;
    private int creditos;

    public Jugador(String alias, Bando bando, int creditos) {
        if (alias == null || alias.isBlank() || creditos < 0)
            throw new IllegalArgumentException("Jugador inválido");
        this.alias = alias;
        this.bando = Objects.requireNonNull(bando, "bando");
        this.creditos = creditos;
    }

    public String alias() { return alias; }
    public Bando bando() { return bando; }
    public int vida() { return vida; }
    public int armadura() { return armadura; }
    public int creditos() { return creditos; }
    public List<ObjetoVirtual> inventario() { return List.copyOf(inventario); }

    public void prepararRonda() {
        // Los PV y la armadura se reinician, como simplificación de una ronda;
        // créditos y objetos NO se reinician para mostrar progreso económico.
        vida = 100;
        armadura = 35;
    }

    public void actualizarVidaVirtual(int puntos) {
        if (puntos < 0) throw new IllegalArgumentException("Puntos negativos");
        // Solo actualiza el contador de PV del personaje digital.
        vida = Math.max(0, vida - puntos);
    }

    public boolean comprar(ObjetoVirtual objeto) {
        Objects.requireNonNull(objeto, "objeto");
        // Una compra solo tiene efecto si el jugador conserva suficientes
        // créditos. Rechazarla no cambia ni saldo ni inventario.
        if (creditos < objeto.precio()) return false;
        // La compra virtual reduce el saldo y registra la ficha del objeto.
        // El HUD podrá consultar después el nombre que realmente compró.
        creditos -= objeto.precio();
        inventario.add(objeto);
        return true;
    }

    public void recibirCreditos(int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("Créditos negativos");
        creditos += cantidad;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde la raíz de 05-patrones-diseno-counter-strike:
 *   javac -encoding UTF-8 -d . MainCounterStrike.java abstract-factory/*.java adapter/*.java builder/*.java chain-of-responsibility/*.java command/*.java composite/*.java decorator/*.java facade/*.java factory/*.java factory-method/*.java infraestructura/*.java observer/*.java strategy/*.java strategy-puntuacion/*.java template-method/*.java
 *   java codetrace.practica.counterstrike.MainCounterStrike
 * Salida esperada:
 *   [ECONOMÍA] Eco: guardar 1600 créditos
 *   [TIENDA] Astra añadió MP9 | créditos=950 | capacidad ilustrativa=30
 *   [ESTADO] tiempo restante=0 s | créditos de Astra=1250
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
