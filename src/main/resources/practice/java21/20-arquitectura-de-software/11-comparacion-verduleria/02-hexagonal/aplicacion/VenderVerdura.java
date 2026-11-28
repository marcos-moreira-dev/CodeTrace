// Tema:
// Misma venta que 20/01. Arquitectura hexagonal: los puertos de aplicación controlan la dirección de dependencias. El adaptador de consola invoca Venta y el repositorio implementa Inventario.
// Rol: Implementa la entrada; depende del contrato de inventario y no de un adaptador concreto.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java aplicacion/puertos/Venta.java aplicacion/puertos/Inventario.java aplicacion/VenderVerdura.java infraestructura/ConsolaVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package aplicacion;

import dominio.Verdura;
import aplicacion.puertos.Venta;
import aplicacion.puertos.Inventario;

public class VenderVerdura implements Venta {
    private final Inventario inventario;
    public VenderVerdura(Inventario inventario) { this.inventario = inventario; }
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
