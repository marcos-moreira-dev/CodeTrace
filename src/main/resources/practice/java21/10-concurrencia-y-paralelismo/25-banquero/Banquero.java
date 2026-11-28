// Tema:
// Algoritmo del banquero: simular una secuencia de finalización con recursos disponibles, asignación y necesidad restante. Si todos pueden terminar, el estado es seguro; inseguro no significa que ya exista deadlock.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Banquero.java MainBanquero.java
// Ejecutar: java MainBanquero

public class Banquero {
    public static boolean seguro(int[] disponible, int[][] asignado, int[][] necesidad) {
        int[] trabajo = disponible.clone();
        boolean[] terminado = new boolean[asignado.length];
        int pendientes = terminado.length;
        while (pendientes > 0) {
            boolean progreso = false;
            for (int i = 0; i < terminado.length; i++) {
                if (terminado[i]) continue;
                boolean alcanza = true;
                for (int r = 0; r < trabajo.length; r++)
                    if (necesidad[i][r] > trabajo[r]) alcanza = false;
                if (!alcanza) continue;
                // Al terminar, el proceso devuelve todos sus recursos asignados.
                for (int r = 0; r < trabajo.length; r++) trabajo[r] += asignado[i][r];
                terminado[i] = true;
                pendientes--;
                progreso = true;
            }
            if (!progreso) return false;
        }
        return true;
    }
}

// Salida esperada:
// Seguro: true
// Seguro: false
