// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.MainCounterStrike
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
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * Se conservan créditos entre rondas: la tienda descuenta 950 solo al comprar.
 * Salida esperada:
 *   [ECONOMÍA] Eco: guardar 1600 créditos
 *   [TIENDA] Astra añadió MP9 | créditos=950 | capacidad ilustrativa=30
 *   [ESTADO] tiempo restante=0 s | créditos de Astra=1250
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
