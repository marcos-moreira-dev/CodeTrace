// Tema:
// Microservicios mínimos: disponibilidad y reservas se ejecutan en JVM hijas distintas, comunicadas por HTTP en loopback y puertos efímeros. Cada servicio posee estado privado. Se omiten persistencia, autenticación, transacciones distribuidas y despliegue de producción; un fallo intermedio puede dejar una reserva incompleta.
// Rol: Servicio independiente que posee el stock de una bicicleta; GET consulta, POST reserva atómicamente en su propia memoria.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build disponibilidad/ServicioDisponibilidad.java reservas/ServicioReservas.java cliente/ClienteAlquiler.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package disponibilidad;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ServicioDisponibilidad {
    private static void responder(HttpExchange ex, int estado, String cuerpo) throws IOException {
        byte[] datos = cuerpo.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        ex.sendResponseHeaders(estado, datos.length);
        ex.getResponseBody().write(datos);
    }

    public static void main(String[] args) throws Exception {
        AtomicInteger stock = new AtomicInteger(1);
        HttpServer servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 4);
        servidor.createContext("/disponibilidad", ex -> {
            try (ex) {
                if (!ex.getRequestMethod().equals("GET")) { responder(ex, 405, "Metodo"); return; }
                responder(ex, 200, Integer.toString(stock.get()));
            }
        });
        servidor.createContext("/reservar", ex -> {
            try (ex) {
                if (!ex.getRequestMethod().equals("POST")) { responder(ex, 405, "Metodo"); return; }
                boolean aceptada = stock.compareAndSet(1, 0);
                responder(ex, aceptada ? 200 : 409, aceptada ? "OK" : "Sin stock");
            }
        });
        servidor.start();
        try {
            System.out.println(servidor.getAddress().getPort());
            System.out.flush();
            System.in.read(); // El padre cierra stdin para detener el servicio.
        } finally { servidor.stop(0); }
    }
}

// Salida esperada:
// Disponibles: 1
// Reserva: R1
// Disponibles: 0
// Reserva rechazada
