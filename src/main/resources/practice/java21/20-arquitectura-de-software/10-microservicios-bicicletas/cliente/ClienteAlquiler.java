// Tema:
// Microservicios mínimos: disponibilidad y reservas se ejecutan en JVM hijas distintas, comunicadas por HTTP en loopback y puertos efímeros. Cada servicio posee estado privado. Se omiten persistencia, autenticación, transacciones distribuidas y despliegue de producción; un fallo intermedio puede dejar una reserva incompleta.
// Rol: Cliente externo que consulta ambos contratos HTTP y verifica códigos; no comparte memoria con servicios.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build disponibilidad/ServicioDisponibilidad.java reservas/ServicioReservas.java cliente/ClienteAlquiler.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package cliente;

import java.net.ProxySelector;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class ClienteAlquiler {
    public static void probar(int disponibilidad, int reservas) throws Exception {
        try (HttpClient cliente = HttpClient.newBuilder().proxy(ProxySelector.of(null))
                .connectTimeout(Duration.ofSeconds(2)).build()) {
            var antes = solicitar(cliente, disponibilidad, "/disponibilidad", false);
            if (antes.statusCode() != 200) throw new IllegalStateException("Consulta");
            System.out.println("Disponibles: " + antes.body());
            var primera = solicitar(cliente, reservas, "/reservas", true);
            if (primera.statusCode() != 201) throw new IllegalStateException("Reserva");
            System.out.println("Reserva: " + primera.body());
            var despues = solicitar(cliente, disponibilidad, "/disponibilidad", false);
            if (despues.statusCode() != 200) throw new IllegalStateException("Consulta");
            System.out.println("Disponibles: " + despues.body());
            var segunda = solicitar(cliente, reservas, "/reservas", true);
            if (segunda.statusCode() != 409) throw new IllegalStateException("Conflicto esperado");
            System.out.println("Reserva rechazada");
        }
    }
    private static HttpResponse<String> solicitar(HttpClient cliente, int puerto, String ruta, boolean post)
            throws Exception {
        var b = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + puerto + ruta))
                .timeout(Duration.ofSeconds(4));
        return cliente.send((post ? b.POST(HttpRequest.BodyPublishers.noBody()) : b.GET()).build(),
                HttpResponse.BodyHandlers.ofString());
    }
}

// Salida esperada:
// Disponibles: 1
// Reserva: R1
// Disponibles: 0
// Reserva rechazada
