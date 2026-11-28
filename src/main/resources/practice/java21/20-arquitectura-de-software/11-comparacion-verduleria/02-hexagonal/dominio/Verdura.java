// Tema:
// Misma venta que 20/01. Arquitectura hexagonal: los puertos de aplicación controlan la dirección de dependencias. El adaptador de consola invoca Venta y el repositorio implementa Inventario.
// Rol: Regla de negocio idéntica a la variante en capas.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java aplicacion/puertos/Venta.java aplicacion/puertos/Inventario.java aplicacion/VenderVerdura.java infraestructura/ConsolaVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package dominio;

public record Verdura(String nombre, int stock) {
    public Verdura {
        if (nombre == null || nombre.isBlank() || stock < 0)
            throw new IllegalArgumentException("Verdura invalida");
    }
    public Verdura vender(int cantidad) {
        if (cantidad <= 0 || cantidad > stock)
            throw new IllegalArgumentException("Stock insuficiente");
        return new Verdura(nombre, stock - cantidad);
    }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
