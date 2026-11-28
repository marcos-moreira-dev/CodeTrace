// Tema:
// Token bucket limita una tasa de envío: los tokens se reponen con reloj simulado y se acumulan hasta una capacidad. No mide congestión ni reproduce el algoritmo de control de TCP.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 TokenBucket.java MainTokenBucket.java
// Ejecutar: java MainTokenBucket

public class MainTokenBucket {
    public static void main(String[] args) throws Exception {
        TokenBucket bucket = new TokenBucket(2, 1);
        for (int t : new int[]{0, 0, 0, 1}) System.out.println(t + ": " + bucket.permitir(t));
    }
}

// Salida esperada:
// 0: true
// 0: true
// 0: false
// 1: true
