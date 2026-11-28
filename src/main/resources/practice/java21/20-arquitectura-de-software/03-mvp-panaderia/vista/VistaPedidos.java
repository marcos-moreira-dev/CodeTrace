// Tema:
// MVP con vista pasiva. La panadería registra cantidades de pan; el presentador controla la vista mediante una interfaz. La vista no lee el modelo ni calcula el precio.
// Rol: Contrato de presentación, permite sustituir consola por un doble de prueba.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/PedidoPan.java vista/VistaPedidos.java vista/ConsolaPedidos.java presentador/PresentadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package vista;

public interface VistaPedidos {
    void mostrarTotal(int centavos);
    void mostrarError(String mensaje);
}

// Salida esperada:
// Total: 100 centavos
// Pedido rechazado
