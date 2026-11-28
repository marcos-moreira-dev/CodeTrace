// Tema:
// Misma venta que 20/01 y 20/11/02. Clean Architecture: la entidad no conoce infraestructura, el interactor usa interfaces interiores y comunica su resultado mediante un límite de salida. El presentador no recibe entidades.
// Rol: Implementa el límite de salida y produce la misma presentación de referencia.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Verdura.java casosdeuso/EntradaVenta.java casosdeuso/SalidaVenta.java casosdeuso/Inventario.java casosdeuso/VenderVerdura.java adaptadores/ControladorVentas.java adaptadores/PresentadorVentas.java infraestructura/InventarioEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package adaptadores;

import casosdeuso.SalidaVenta;

public class PresentadorVentas implements SalidaVenta {
    public void aceptada(String nombre, int cantidad, int stock) {
        System.out.println("Venta: " + nombre + " x" + cantidad);
        System.out.println("Stock: " + stock);
    }
    public void rechazada(int stock) {
        System.out.println("Venta rechazada");
        System.out.println("Stock: " + stock);
    }
}

// Salida esperada:
// Venta: tomate x2
// Stock: 3
// Venta rechazada
// Stock: 3
