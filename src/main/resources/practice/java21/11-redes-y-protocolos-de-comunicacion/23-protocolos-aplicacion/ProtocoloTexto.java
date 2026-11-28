// Tema:
// Un protocolo de aplicación define una gramática y errores. Modelo de solicitud CONSULTAR producto, con longitud y caracteres permitidos. El analizador separa sintaxis de reglas del dominio; no usa sockets.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Solicitud.java Respuesta.java ProtocoloTexto.java MainProtocolo.java
// Ejecutar: java MainProtocolo

public class ProtocoloTexto {
    public Respuesta procesar(String texto) {
        Solicitud solicitud;
        try { solicitud = Solicitud.analizar(texto); }
        catch (IllegalArgumentException e) { return new Respuesta(400, "Sintaxis invalida"); }
        if (solicitud.producto().equals("pan")) return new Respuesta(200, "12");
        return new Respuesta(404, "No encontrado");
    }
}

// Salida esperada:
// 200 12
// 404 No encontrado
// 400 Sintaxis invalida
