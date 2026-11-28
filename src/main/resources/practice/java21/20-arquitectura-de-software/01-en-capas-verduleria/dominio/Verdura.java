// Tema:
// Arquitectura en capas. Una verdulería vende tomates; presentación llama al servicio, que utiliza la persistencia concreta. El dominio valida existencias. Las dependencias van hacia las capas inferiores.
// Rol: Entidad inmutable; aplica la regla de negocio sin conocer la consola.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java persistencia/InventarioEnMemoria.java servicio/ServicioVentas.java presentacion/ConsolaVentas.java Main.java
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
