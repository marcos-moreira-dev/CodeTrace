// Tema:
// A* selecciona la celda con menor g+h; g es coste conocido y h la distancia Manhattan, admisible y consistente con pasos ortogonales unitarios. Se usa una cuadrícula pequeña, sin diagonales; coste óptimo conocido 6 por la cota Manhattan y un camino factible.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 BusquedaAEstrella.java
// Ejecutar: java BusquedaAEstrella

public class BusquedaAEstrella {
    static int buscar(int[][] mapa) {
        int alto = mapa.length, ancho = mapa[0].length;
        int[][] g = new int[alto][ancho];
        boolean[][] cerrado = new boolean[alto][ancho];
        for (int[] fila : g) java.util.Arrays.fill(fila, Integer.MAX_VALUE);
        g[0][0] = 0;
        int[] dx = {1,-1,0,0}, dy = {0,0,1,-1};
        while (true) {
            int bx = -1, by = -1, mejor = Integer.MAX_VALUE;
            for (int y = 0; y < alto; y++)
                for (int x = 0; x < ancho; x++) {
                    if (cerrado[y][x] || g[y][x] == Integer.MAX_VALUE) continue;
                    int f = g[y][x] + (ancho - 1 - x) + (alto - 1 - y);
                    if (f < mejor) { mejor = f; bx = x; by = y; }
                }
            if (bx == -1) return -1;
            if (bx == ancho - 1 && by == alto - 1) return g[by][bx];
            cerrado[by][bx] = true;
            for (int i = 0; i < 4; i++) {
                int x = bx + dx[i], y = by + dy[i];
                if (x < 0 || x >= ancho || y < 0 || y >= alto || mapa[y][x] != 0 || cerrado[y][x]) continue;
                g[y][x] = Math.min(g[y][x], g[by][bx] + 1);
            }
        }
    }

    public static void main(String[] args) {
        int[][] mapa = {{0,0,0,0},{0,1,1,0},{0,0,0,0},{0,0,0,0}};
        int coste = buscar(mapa);
        System.out.println("Coste: " + coste);
        System.out.println("Optimo conocido: " + (coste == 6));
    }
}

// Salida esperada:
// Coste: 6
// Optimo conocido: true
