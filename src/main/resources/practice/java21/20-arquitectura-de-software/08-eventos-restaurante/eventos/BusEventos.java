// Tema:
// Arquitectura orientada a eventos. Cocina cambia el estado de un pedido y publica PedidoListo; el mostrador se suscribe sin que Cocina lo conozca. Bus síncrono local, sin persistencia, broker ni garantías de entrega distribuida.
// Rol: Registro de suscriptores y distribución síncrona; una copia protege el recorrido ante nuevas suscripciones.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build dominio/Pedido.java eventos/PedidoListo.java eventos/BusEventos.java cocina/Cocina.java mostrador/AvisadorPedidos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package eventos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BusEventos {
    private final List<Consumer<PedidoListo>> suscriptores = new ArrayList<>();
    public void suscribir(Consumer<PedidoListo> escucha) { suscriptores.add(escucha); }
    public void publicar(PedidoListo evento) {
        for (var escucha : List.copyOf(suscriptores)) escucha.accept(evento);
    }
}

// Salida esperada:
// Mostrador: pedido 7 listo
// Pedido ya terminado
