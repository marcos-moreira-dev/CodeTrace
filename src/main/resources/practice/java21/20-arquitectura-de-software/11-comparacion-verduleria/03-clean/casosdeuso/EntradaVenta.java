// Tema:
// Misma venta que 20/01 y 20/11/02. Clean Architecture: la entidad no conoce infraestructura, el interactor usa interfaces interiores y comunica su resultado mediante un límite de salida. El presentador no recibe entidades.
// Rol: Límite de entrada del interactor.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Verdura.java casosdeuso/EntradaVenta.java casosdeuso/SalidaVenta.java casosdeuso/Inventario.java casosdeuso/VenderVerdura.java adaptadores/ControladorVentas.java adaptadores/PresentadorVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package casosdeuso;

public interface EntradaVenta {
    void vender(int cantidad);
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
