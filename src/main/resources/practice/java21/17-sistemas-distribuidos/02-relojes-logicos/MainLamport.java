// Tema:
// Reloj de Lamport: cada evento local o envío incrementa el contador; al recibir se calcula max(local, recibido)+1. Si A precede causalmente a B, L(A)<L(B), pero la desigualdad por sí sola no demuestra causalidad.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 RelojLamport.java MainLamport.java
// Ejecutar: java MainLamport

public class MainLamport {
    public static void main(String[] args) {
        RelojLamport a = new RelojLamport(), b = new RelojLamport();
        System.out.println("A local: " + a.evento());
        long marca = a.enviar();
        System.out.println("A envia: " + marca);
        System.out.println("B recibe: " + b.recibir(marca));
        System.out.println("B local: " + b.evento());
    }
}

// Salida esperada:
// A local: 1
// A envia: 2
// B recibe: 3
// B local: 4
