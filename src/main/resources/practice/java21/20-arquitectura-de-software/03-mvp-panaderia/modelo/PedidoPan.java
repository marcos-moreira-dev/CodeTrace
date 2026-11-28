// Tema:
// MVP con vista pasiva. La panadería registra cantidades de pan; el presentador controla la vista mediante una interfaz. La vista no lee el modelo ni calcula el precio.
// Rol: Reglas del pedido y precio en centavos enteros.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/PedidoPan.java vista/VistaPedidos.java vista/ConsolaPedidos.java presentador/PresentadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package modelo;

public record PedidoPan(int cantidad) {
    public PedidoPan {
        if (cantidad < 1 || cantidad > 100) throw new IllegalArgumentException("Cantidad");
    }
    public int totalCentavos() { return cantidad * 25; }
}

// Salida esperada:
// Total: 100 centavos
// Pedido rechazado
