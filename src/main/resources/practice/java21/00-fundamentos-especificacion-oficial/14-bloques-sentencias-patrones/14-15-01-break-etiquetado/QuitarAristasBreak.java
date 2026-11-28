// Tema:
// break con una etiqueta termina la sentencia etiquetada correspondiente. Permite salir de una
// búsqueda interna y continuar después de su bloque.
// Compilar: javac --release 21 -encoding UTF-8 -d . QuitarAristasBreak.java
// Ejecutar: java QuitarAristasBreak
// Comandos desde la carpeta de la unidad.

class QuitarAristasBreak {
    static int[][] quitar(int[][] aristas, int a, int b) {
        int[][] resultado = new int[aristas.length][];
        for (int k = 0; k < aristas.length; k++) {
            fila: {
                int pos;
                buscar: {
                    if (k == a || k == b) {
                        int destino = k == a ? b : a;
                        for (pos = 0; pos < aristas[k].length; pos++)
                            if (aristas[k][pos] == destino) break buscar;
                    }
                    resultado[k] = aristas[k];
                    break fila;
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
