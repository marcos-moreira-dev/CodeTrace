// Tema:
// Microservicios mínimos: disponibilidad y reservas se ejecutan en JVM hijas distintas, comunicadas por HTTP en loopback y puertos efímeros. Cada servicio posee estado privado. Se omiten persistencia, autenticación, transacciones distribuidas y despliegue de producción; un fallo intermedio puede dejar una reserva incompleta.
// Rol: Servicio independiente que emite recibos; consume disponibilidad exclusivamente mediante contrato HTTP, nunca importa sus clases.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build disponibilidad/ServicioDisponibilidad.java reservas/ServicioReservas.java cliente/ClienteAlquiler.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package reservas;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ServicioReservas {
    private static void responder(HttpExchange ex, int estado, String cuerpo) throws IOException {
        byte[] datos = cuerpo.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        ex.sendResponseHeaders(estado, datos.length);
        ex.getResponseBody().write(datos);
    }

    public static void main(String[] args) throws Exception {
        int puertoDisponibilidad = Integer.parseInt(args[0]);
        AtomicInteger recibos = new AtomicInteger();
        try (HttpClient cliente = HttpClient.newBuilder().proxy(ProxySelector.of(null))
                .connectTimeout(Duration.ofSeconds(2)).build()) {
            HttpServer servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 4);
            servidor.createContext("/reservas", ex -> {
                try (ex) {
                    if (!ex.getRequestMethod().equals("POST")) { responder(ex, 405, "Metodo"); return; }
                    HttpRequest solicitud = HttpRequest.newBuilder(
                            URI.create("http://127.0.0.1:" + puertoDisponibilidad + "/reservar"))
                            .timeout(Duration.ofSeconds(3)).POST(HttpRequest.BodyPublishers.noBody()).build();
                    HttpResponse<String> r;
                    try { r = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString()); }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); responder(ex, 503, "Interrumpido"); return;
                    } catch (IOException e) { responder(ex, 503, "No disponible"); return; }
                    if (r.statusCode() == 200) responder(ex, 201, "R" + recibos.incrementAndGet());
                    else if (r.statusCode() == 409) responder(ex, 409, "Sin stock");
                    else responder(ex, 503, "No disponible");
                }
            });
            servidor.start();
            try {
                System.out.println(servidor.getAddress().getPort());
                System.out.flush();
                System.in.read();
            } finally { servidor.stop(0); }
        }
    }
}

// Salida esperada:
// Disponibles: 1
// Reserva: R1
// Disponibles: 0
// Reserva rechazada
