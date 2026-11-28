// Tema:
// Misma venta que 20/01. Arquitectura hexagonal: los puertos de aplicación controlan la dirección de dependencias. El adaptador de consola invoca Venta y el repositorio implementa Inventario.
// Rol: Conecta puertos y adaptadores; repite el escenario de capas.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Verdura.java aplicacion/puertos/Venta.java aplicacion/puertos/Inventario.java aplicacion/VenderVerdura.java infraestructura/ConsolaVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import aplicacion.VenderVerdura;
import infraestructura.ConsolaVentas;
import infraestructura.InventarioEnMemoria;

public class Main {
    public static void main(String[] args) {
        var consola = new ConsolaVentas(new VenderVerdura(new InventarioEnMemoria()));
        consola.vender(2);
        consola.vender(4);
    }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
