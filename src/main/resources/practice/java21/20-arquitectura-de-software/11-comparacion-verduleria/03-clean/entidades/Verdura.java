// Tema:
// Misma venta que 20/01 y 20/11/02. Clean Architecture: la entidad no conoce infraestructura, el interactor usa interfaces interiores y comunica su resultado mediante un límite de salida. El presentador no recibe entidades.
// Rol: Entidad con idéntica regla de negocio.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Verdura.java casosdeuso/EntradaVenta.java casosdeuso/SalidaVenta.java casosdeuso/Inventario.java casosdeuso/VenderVerdura.java adaptadores/ControladorVentas.java adaptadores/PresentadorVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package entidades;

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
