// Tema:
// El barrido conceptual identifica nodos no marcados como recuperables. El nodo 3 tiene un
// ciclo propio, pero no es alcanzable desde la raíz 0; esto no ejecuta el GC real.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Alcanzabilidad.java MainMarcarBarrer.java
// Ejecutar: java MainMarcarBarrer

public class MainMarcarBarrer {
    public static void main(String[] args) {
        int[][] referencias = {{1}, {2}, {1}, {3}};
        boolean[] marcado = new boolean[referencias.length];
        Alcanzabilidad.marcar(0, referencias, marcado);
        for (int i = 0; i < marcado.length; i++) {
            System.out.println(i + (marcado[i] ? " vivo" : " recuperable"));
        }
    }
}

// Salida esperada:
// 0 vivo
// 1 vivo
// 2 vivo
// 3 recuperable
