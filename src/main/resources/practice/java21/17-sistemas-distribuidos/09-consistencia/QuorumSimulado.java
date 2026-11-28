// Tema:
// Con N réplicas fijas, R+W>N implica intersección entre cualquier conjunto de lectura y escritura. Leemos la mayor versión confirmada de un modelo con escritor único, sin conflictos. La intersección sola no garantiza consenso ni linealizabilidad.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 QuorumSimulado.java
// Ejecutar: java QuorumSimulado

public class QuorumSimulado {
    public static void main(String[] args) {
        int n = 3, r = 2, w = 2;
        int[] versiones = {2, 2, 1};
        int[] valores = {20, 20, 10};
        int[] lectura = {1, 2};
        int mejor = lectura[0];
        for (int i : lectura) if (versiones[i] > versiones[mejor]) mejor = i;
        System.out.println("Interseccion: " + (r + w > n));
        System.out.println("Valor: " + valores[mejor]);
    }
}

// Salida esperada:
// Interseccion: true
// Valor: 20
