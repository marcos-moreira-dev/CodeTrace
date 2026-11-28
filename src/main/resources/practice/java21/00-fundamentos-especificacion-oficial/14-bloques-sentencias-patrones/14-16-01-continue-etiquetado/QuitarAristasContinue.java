// Tema:
// continue con una etiqueta salta a la siguiente iteración del bucle etiquetado. Es diferente
// de break, que termina la sentencia.
// Compilar: javac --release 21 -encoding UTF-8 -d . QuitarAristasContinue.java
// Ejecutar: java QuitarAristasContinue
// Comandos desde la carpeta de la unidad.

class QuitarAristasContinue {
    static int[][] quitar(int[][] aristas, int a, int b) {
        int[][] resultado = new int[aristas.length][];
        filas: for (int k = 0; k < aristas.length; k++) {
            {
                int pos;
                buscar: {
                    if (k == a || k == b) {
                        int destino = k == a ? b : a;
                        for (pos = 0; pos < aristas[k].length; pos++)
                            if (aristas[k][pos] == destino) break buscar;
                    }
                    resultado[k] = aristas[k];
                    continue filas;
                }
                int[] nueva = new int[aristas[k].length - 1];
                System.arraycopy(aristas[k], 0, nueva, 0, pos);
                System.arraycopy(aristas[k], pos + 1, nueva, pos, nueva.length - pos);
                resultado[k] = nueva;
            }
        }
        return resultado;
    }
    public static void main(String[] args) {
        int[][] grafo = {{1, 2}, {0, 2}, {1}};
        int[][] copia = quitar(grafo, 0, 1);
        System.out.println(java.util.Arrays.deepToString(copia));
        System.out.println(copia[2] == grafo[2]);
    }
}

// Salida esperada:
// [[2], [2], [1]]
// true
