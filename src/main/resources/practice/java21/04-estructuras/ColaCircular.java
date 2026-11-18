public final class ColaCircular {
    private final int[] datos;
    private int inicio;
    private int siguiente;
    private int cantidad;

    public ColaCircular(int capacidad) {
        if (capacidad <= 0) throw new IllegalArgumentException("Capacidad inválida");
        datos = new int[capacidad];
    }

    public void encolar(int valor) {
        if (cantidad == datos.length) throw new IllegalStateException("Cola llena");
        datos[siguiente] = valor;
        siguiente = (siguiente + 1) % datos.length;
        cantidad++;
    }

    public int desencolar() {
        if (cantidad == 0) throw new IllegalStateException("Cola vacía");
        int valor = datos[inicio];
        inicio = (inicio + 1) % datos.length;
        cantidad--;
        return valor;
    }

    public int cantidad() {
        return cantidad;
    }
}
