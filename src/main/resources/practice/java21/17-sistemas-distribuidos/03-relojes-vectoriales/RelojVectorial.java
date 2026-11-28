// Tema:
// Cada participante conserva un vector de contadores. Al recibir combina máximos componente a componente e incrementa su posición. Dos vectores incomparables representan eventos concurrentes en este modelo.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 RelojVectorial.java MainCausalidad.java
// Ejecutar: java MainCausalidad

public class RelojVectorial {
    private final int id;
    private final int[] reloj;
    public RelojVectorial(int id, int n) {
        if (n <= 0 || id < 0 || id >= n) throw new IllegalArgumentException("Nodo");
        this.id = id;
        reloj = new int[n];
    }
    public int[] evento() { reloj[id]++; return reloj.clone(); }
    public int[] recibir(int[] otro) {
        if (otro.length != reloj.length) throw new IllegalArgumentException("Dimension");
        for (int i = 0; i < reloj.length; i++) reloj[i] = Math.max(reloj[i], otro[i]);
        return evento();
    }
    public static boolean precede(int[] a, int[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Dimension");
        boolean menor = false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > b[i]) return false;
            if (a[i] < b[i]) menor = true;
        }
        return menor;
    }
    public static boolean concurrentes(int[] a, int[] b) {
        return !java.util.Arrays.equals(a,b) && !precede(a,b) && !precede(b,a);
    }
}

// Salida esperada:
// A: [1, 0]
// B: [0, 1]
// Concurrentes: true
// B recibe: [1, 2]
// A precede: true
