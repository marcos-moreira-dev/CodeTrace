// Tema:
// HTTP define método, destino, estado, cabeceras y cuerpo. HttpServer y HttpClient son APIs del JDK, sin Spring. El servidor escucha exclusivamente en 127.0.0.1 y se detiene al finalizar.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorHttp.java ClienteHttp.java MainHttp.java
// Ejecutar: java MainHttp

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.io.IOException;

public class ServidorHttp implements AutoCloseable {
    private final HttpServer servidor;
    public ServidorHttp() throws IOException {
        servidor = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 4);
        servidor.createContext("/inventario", intercambio -> {
            try (intercambio) {
                boolean get = intercambio.getRequestMethod().equals("GET");
                int estado = get ? 200 : 405;
                byte[] cuerpo = (get ? "Pan: 12" : "Metodo no permitido").getBytes(StandardCharsets.UTF_8);
                intercambio.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
                if (!get) intercambio.getResponseHeaders().set("Allow", "GET");
                intercambio.sendResponseHeaders(estado, cuerpo.length);
                intercambio.getResponseBody().write(cuerpo);
            }
        });
        servidor.start();
    }
    public int puerto() { return servidor.getAddress().getPort(); }
    public void close() { servidor.stop(0); }
}

// Salida esperada:
// Estado: 200
// Tipo: text/plain; charset=utf-8
// Pan: 12
