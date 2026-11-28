// Tema:
// En un grafo de espera con recursos exclusivos de una instancia, un ciclo indica espera circular. Se analiza un arreglo de dependencias sin crear hilos bloqueados.
// Modalidad: ejecución y modelo explícito.
// Compilar: javac --release 21 -encoding UTF-8 DetectarDeadlockSimulado.java
// Ejecutar: java DetectarDeadlockSimulado

public class DetectarDeadlockSimulado {
    static boolean ciclo(int[] espera) {
        for (int i = 0; i < espera.length; i++) {
            boolean[] visto = new boolean[espera.length];
            int actual = i;
            while (actual != -1) {
                if (visto[actual]) return true;
                visto[actual] = true;
                actual = espera[actual];
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Caso A: " + ciclo(new int[]{1, 2, 0}));
        System.out.println("Caso B: " + ciclo(new int[]{1, 2, -1}));
    }
}

// Salida esperada:
// Caso A: true
// Caso B: false
