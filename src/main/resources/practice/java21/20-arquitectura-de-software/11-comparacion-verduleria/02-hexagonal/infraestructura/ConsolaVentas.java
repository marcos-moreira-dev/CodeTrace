// Tema:
// Misma venta que 20/01. Arquitectura hexagonal: los puertos de aplicación controlan la dirección de dependencias. El adaptador de consola invoca Venta y el repositorio implementa Inventario.
// Rol: Adaptador de entrada; depende del puerto, no de la clase VenderVerdura.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java aplicacion/puertos/Venta.java aplicacion/puertos/Inventario.java aplicacion/VenderVerdura.java infraestructura/ConsolaVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package infraestructura;

import aplicacion.puertos.Venta;
import dominio.Verdura;

public class ConsolaVentas {
    private final Venta venta;
    public ConsolaVentas(Venta venta) { this.venta = venta; }
    public void vender(int cantidad) {
        try {
            Verdura resultado = venta.vender(cantidad);
            System.out.println("Venta: " + resultado.nombre() + " x" + cantidad);
        } catch (IllegalArgumentException e) { System.out.println("Venta rechazada"); }
        System.out.println("Stock: " + venta.stock());
    }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
