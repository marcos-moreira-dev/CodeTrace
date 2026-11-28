// Tema:
// Arquitectura orientada a eventos. Cocina cambia el estado de un pedido y publica PedidoListo; el mostrador se suscribe sin que Cocina lo conozca. Bus síncrono local, sin persistencia, broker ni garantías de entrega distribuida.
// Rol: Entidad que impide marcar dos veces el mismo pedido.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Pedido.java eventos/PedidoListo.java eventos/BusEventos.java cocina/Cocina.java mostrador/AvisadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package dominio;

public class Pedido {
    private final int numero;
    private boolean listo;
    public Pedido(int numero) { this.numero = numero; }
    public int numero() { return numero; }
    public void marcarListo() {
        if (listo) throw new IllegalStateException("Ya listo");
        listo = true;
    }
}

// Salida esperada:
// Mostrador: pedido 7 listo
// Pedido ya terminado
