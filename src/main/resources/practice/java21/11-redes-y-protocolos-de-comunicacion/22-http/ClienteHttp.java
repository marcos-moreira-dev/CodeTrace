// Tema:
// HTTP define método, destino, estado, cabeceras y cuerpo. HttpServer y HttpClient son APIs del JDK, sin Spring. El servidor escucha exclusivamente en 127.0.0.1 y se detiene al finalizar.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorHttp.java ClienteHttp.java MainHttp.java
// Ejecutar: java MainHttp

import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class ClienteHttp {
    public static void consultar(int puerto) throws Exception {
        try (HttpClient cliente = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3))
                .proxy(java.net.ProxySelector.of(null)).build()) {
            HttpRequest solicitud = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + puerto + "/inventario"))
                    .timeout(Duration.ofSeconds(4)).GET().build();
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            System.out.println("Estado: " + respuesta.statusCode());
            System.out.println("Tipo: " + respuesta.headers().firstValue("Content-Type").orElse(""));
            System.out.println(respuesta.body());
        }
    }
}

// Salida esperada:
// Estado: 200
// Tipo: text/plain; charset=utf-8
// Pan: 12
