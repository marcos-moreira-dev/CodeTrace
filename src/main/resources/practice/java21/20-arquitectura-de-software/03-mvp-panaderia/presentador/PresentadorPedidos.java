// Tema:
// MVP con vista pasiva. La panadería registra cantidades de pan; el presentador controla la vista mediante una interfaz. La vista no lee el modelo ni calcula el precio.
// Rol: Construye el modelo, transforma resultado y dirige el contrato de vista.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/PedidoPan.java vista/VistaPedidos.java vista/ConsolaPedidos.java presentador/PresentadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package presentador;

import modelo.PedidoPan;
import vista.VistaPedidos;

public class PresentadorPedidos {
    private final VistaPedidos vista;
    public PresentadorPedidos(VistaPedidos vista) { this.vista = vista; }
    public void registrar(int cantidad) {
        try {
            PedidoPan pedido = new PedidoPan(cantidad);
            vista.mostrarTotal(pedido.totalCentavos());
        } catch (IllegalArgumentException e) { vista.mostrarError("Pedido rechazado"); }
    }
}

// Salida esperada:
// Total: 100 centavos
// Pedido rechazado
