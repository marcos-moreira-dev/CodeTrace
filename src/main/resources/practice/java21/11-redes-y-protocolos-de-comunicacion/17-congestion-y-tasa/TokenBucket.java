// Tema:
// Token bucket limita una tasa de envío: los tokens se reponen con reloj simulado y se acumulan hasta una capacidad. No mide congestión ni reproduce el algoritmo de control de TCP.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 TokenBucket.java MainTokenBucket.java
// Ejecutar: java MainTokenBucket

public class TokenBucket {
    private final int capacidad, tasa;
    private int tokens, ultimo;
    public TokenBucket(int capacidad, int tasa) {
        if (capacidad <= 0 || tasa <= 0) throw new IllegalArgumentException();
        this.capacidad = capacidad; this.tasa = tasa; tokens = capacidad;
    }
    public boolean permitir(int instante) {
        if (instante < ultimo) throw new IllegalArgumentException("Reloj");
        tokens = (int) Math.min(capacidad, tokens + (long) (instante - ultimo) * tasa);
        ultimo = instante;
        if (tokens == 0) return false;
        tokens--;
        return true;
    }
}

// Salida esperada:
// 0: true
// 0: true
// 0: false
// 1: true
