// Tema:
// MVP con vista pasiva. La panadería registra cantidades de pan; el presentador controla la vista mediante una interfaz. La vista no lee el modelo ni calcula el precio.
// Rol: Compone la aplicación y simula dos eventos.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/PedidoPan.java vista/VistaPedidos.java vista/ConsolaPedidos.java presentador/PresentadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import presentador.PresentadorPedidos;
import vista.ConsolaPedidos;

public class Main {
    public static void main(String[] args) {
        var presentador = new PresentadorPedidos(new ConsolaPedidos());
        presentador.registrar(4);
        presentador.registrar(0);
    }
}

// Salida esperada:
// Total: 100 centavos
// Pedido rechazado
