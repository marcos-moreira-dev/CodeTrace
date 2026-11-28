// Tema:
// Un invariante debe mantenerse después de cada operación pública. El inventario nunca puede quedar negativo ni superar su capacidad; los cambios inválidos no alteran el estado.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 Invariantes.java
// Ejecutar: java Invariantes

public class Invariantes {
    private final int capacidad;
    private int cantidad;
    public Invariantes(int capacidad) {
        if (capacidad < 0) throw new IllegalArgumentException("Capacidad");
        this.capacidad = capacidad;
    }
    public void agregar(int n) {
        if (n < 0 || n > capacidad - cantidad) throw new IllegalArgumentException("Cantidad");
        cantidad += n;
        comprobar();
    }
    public void retirar(int n) {
        if (n < 0 || n > cantidad) throw new IllegalArgumentException("Cantidad");
        cantidad -= n;
        comprobar();
    }
    private void comprobar() {
        if (cantidad < 0 || cantidad > capacidad) throw new AssertionError("Invariante");
    }

    public static void main(String[] args) throws Exception {
        Invariantes stock = new Invariantes(10);
        stock.agregar(7);
        stock.retirar(2);
        try { stock.retirar(6); }
        catch (IllegalArgumentException e) { System.out.println("Retiro rechazado"); }
        System.out.println("Stock: " + stock.cantidad);
    }
}

// Salida esperada:
// Retiro rechazado
// Stock: 5
