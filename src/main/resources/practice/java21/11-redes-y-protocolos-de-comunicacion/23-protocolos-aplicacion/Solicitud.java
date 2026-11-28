// Tema:
// Un protocolo de aplicación define una gramática y errores. Modelo de solicitud CONSULTAR producto, con longitud y caracteres permitidos. El analizador separa sintaxis de reglas del dominio; no usa sockets.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Solicitud.java Respuesta.java ProtocoloTexto.java MainProtocolo.java
// Ejecutar: java MainProtocolo

public record Solicitud(String producto) {
    public static Solicitud analizar(String linea) {
        if (linea == null || linea.length() > 64 || !linea.matches("CONSULTAR [a-z]{1,20}"))
            throw new IllegalArgumentException("Sintaxis");
        return new Solicitud(linea.substring("CONSULTAR ".length()));
    }
}

// Salida esperada:
// 200 12
// 404 No encontrado
// 400 Sintaxis invalida
