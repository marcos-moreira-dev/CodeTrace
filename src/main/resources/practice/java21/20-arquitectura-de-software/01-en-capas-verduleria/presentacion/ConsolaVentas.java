// Tema:
// Arquitectura en capas. Una verdulería vende tomates; presentación llama al servicio, que utiliza la persistencia concreta. El dominio valida existencias. Las dependencias van hacia las capas inferiores.
// Rol: Traduce una acción de usuario en llamada al servicio y presenta el resultado.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java persistencia/InventarioEnMemoria.java servicio/ServicioVentas.java presentacion/ConsolaVentas.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package presentacion;

import servicio.ServicioVentas;
import dominio.Verdura;

public class ConsolaVentas {
    private final ServicioVentas servicio;
    public ConsolaVentas(ServicioVentas servicio) { this.servicio = servicio; }

    public void vender(int cantidad) {
        try {
            Verdura resultado = servicio.vender(cantidad);
            System.out.println("Venta: " + resultado.nombre() + " x" + cantidad);
        } catch (IllegalArgumentException e) {
            System.out.println("Venta rechazada");
        }
        System.out.println("Stock: " + servicio.stock());
    }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
