// Tema:
// Monolito modular. Compras y ventas son módulos dentro de una JVM; ambos consumen la API pública de inventario sin depender entre sí. La clase Existencias tiene acceso de paquete para ocultar los detalles internos.
// Rol: Implementación interna del módulo, no es accesible desde otros paquetes.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build inventario/Existencias.java inventario/Inventario.java ventas/Ventas.java compras/Compras.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package inventario;

final class Existencias {
    private int cantidad;
    int cantidad() { return cantidad; }
    void agregar(int n) {
        if (n <= 0) throw new IllegalArgumentException("Cantidad");
        cantidad = Math.addExact(cantidad, n);
    }
    void retirar(int n) {
        if (n <= 0 || n > cantidad) throw new IllegalArgumentException("Stock insuficiente");
        cantidad -= n;
    }
}

// Salida esperada:
// Compra: 10
// Venta: 4
// Venta rechazada
// Stock: 6
