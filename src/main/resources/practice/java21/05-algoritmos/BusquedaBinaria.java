public final class BusquedaBinaria {
    public static int buscar(int[] ordenados, int objetivo) {
        int izquierda = 0;
        int derecha = ordenados.length - 1;
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            if (ordenados[medio] == objetivo) return medio;
            if (ordenados[medio] < objetivo) izquierda = medio + 1;
            else derecha = medio - 1;
        }
        return -1;
    }
}
