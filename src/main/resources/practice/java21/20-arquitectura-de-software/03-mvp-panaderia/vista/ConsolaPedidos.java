// Tema:
// MVP con vista pasiva. La panadería registra cantidades de pan; el presentador controla la vista mediante una interfaz. La vista no lee el modelo ni calcula el precio.
// Rol: Adaptador pasivo: solo imprime lo que le indica el presentador.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/PedidoPan.java vista/VistaPedidos.java vista/ConsolaPedidos.java presentador/PresentadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package vista;

public class ConsolaPedidos implements VistaPedidos {
    public void mostrarTotal(int centavos) {
        System.out.println("Total: " + centavos + " centavos");
    }
    public void mostrarError(String mensaje) {
        System.out.println(mensaje);
    }
}

// Salida esperada:
// Total: 100 centavos
// Pedido rechazado
