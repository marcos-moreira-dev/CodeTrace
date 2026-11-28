// Tema:
// Reloj de Lamport: cada evento local o envío incrementa el contador; al recibir se calcula max(local, recibido)+1. Si A precede causalmente a B, L(A)<L(B), pero la desigualdad por sí sola no demuestra causalidad.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 RelojLamport.java MainLamport.java
// Ejecutar: java MainLamport

public class RelojLamport {
    private long tiempo;
    public long evento() { return ++tiempo; }
    public long enviar() { return evento(); }
    public long recibir(long marca) { tiempo = Math.max(tiempo, marca) + 1; return tiempo; }
}

// Salida esperada:
// A local: 1
// A envia: 2
// B recibe: 3
// B local: 4
