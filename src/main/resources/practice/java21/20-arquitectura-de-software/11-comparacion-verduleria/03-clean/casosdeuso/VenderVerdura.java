// Tema:
// Misma venta que 20/01 y 20/11/02. Clean Architecture: la entidad no conoce infraestructura, el interactor usa interfaces interiores y comunica su resultado mediante un límite de salida. El presentador no recibe entidades.
// Rol: Interactor: aplica la regla, guarda el cambio y notifica el límite de salida.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Verdura.java casosdeuso/EntradaVenta.java casosdeuso/SalidaVenta.java casosdeuso/Inventario.java casosdeuso/VenderVerdura.java adaptadores/ControladorVentas.java adaptadores/PresentadorVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package casosdeuso;

import entidades.Verdura;

public class VenderVerdura implements EntradaVenta {
    private final Inventario inventario;
    private final SalidaVenta salida;
    public VenderVerdura(Inventario inventario, SalidaVenta salida) {
        this.inventario = inventario; this.salida = salida;
    }
    public void vender(int cantidad) {
        Verdura anterior = inventario.leer();
        Verdura nueva;
        try { nueva = anterior.vender(cantidad); }
        catch (IllegalArgumentException e) { salida.rechazada(anterior.stock()); return; }
        inventario.guardar(nueva);
        salida.aceptada(nueva.nombre(), cantidad, nueva.stock());
    }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
