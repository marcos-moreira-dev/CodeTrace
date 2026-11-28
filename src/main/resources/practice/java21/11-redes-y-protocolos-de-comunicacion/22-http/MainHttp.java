// Tema:
// HTTP define método, destino, estado, cabeceras y cuerpo. HttpServer y HttpClient son APIs del JDK, sin Spring. El servidor escucha exclusivamente en 127.0.0.1 y se detiene al finalizar.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorHttp.java ClienteHttp.java MainHttp.java
// Ejecutar: java MainHttp

public class MainHttp {
    public static void main(String[] args) throws Exception {
        try (ServidorHttp servidor = new ServidorHttp()) {
            ClienteHttp.consultar(servidor.puerto());
        }
    }
}

// Salida esperada:
// Estado: 200
// Tipo: text/plain; charset=utf-8
// Pan: 12
