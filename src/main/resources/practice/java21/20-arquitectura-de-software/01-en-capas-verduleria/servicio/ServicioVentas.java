// Tema:
// Arquitectura en capas. Una verdulería vende tomates; presentación llama al servicio, que utiliza la persistencia concreta. El dominio valida existencias. Las dependencias van hacia las capas inferiores.
// Rol: Coordina la venta y la persistencia; conoce la implementación concreta en esta arquitectura.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java persistencia/InventarioEnMemoria.java servicio/ServicioVentas.java presentacion/ConsolaVentas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package servicio;

import dominio.Verdura;
import persistencia.InventarioEnMemoria;

public class ServicioVentas {
    private final InventarioEnMemoria inventario;
    public ServicioVentas(InventarioEnMemoria inventario) { this.inventario = inventario; }

    public Verdura vender(int cantidad) {
        Verdura nueva = inventario.leer().vender(cantidad);
        inventario.guardar(nueva);
        return nueva;
    }

    public int stock() { return inventario.leer().stock(); }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
