// Tema:
// Arquitectura orientada a eventos. Cocina cambia el estado de un pedido y publica PedidoListo; el mostrador se suscribe sin que Cocina lo conozca. Bus síncrono local, sin persistencia, broker ni garantías de entrega distribuida.
// Rol: Productor del evento: conoce el bus, no los consumidores.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Pedido.java eventos/PedidoListo.java eventos/BusEventos.java cocina/Cocina.java mostrador/AvisadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package cocina;

import dominio.Pedido;
import eventos.BusEventos;
import eventos.PedidoListo;

public class Cocina {
    private final BusEventos bus;
    public Cocina(BusEventos bus) { this.bus = bus; }
    public void terminar(Pedido pedido) {
        pedido.marcarListo();
        bus.publicar(new PedidoListo(pedido.numero()));
    }
}

// Salida esperada:
// Mostrador: pedido 7 listo
// Pedido ya terminado
