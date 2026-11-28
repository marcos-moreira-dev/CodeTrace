// Tema:
// Un protocolo de aplicación define una gramática y errores. Modelo de solicitud CONSULTAR producto, con longitud y caracteres permitidos. El analizador separa sintaxis de reglas del dominio; no usa sockets.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Solicitud.java Respuesta.java ProtocoloTexto.java MainProtocolo.java
// Ejecutar: java MainProtocolo

public class MainProtocolo {
    public static void main(String[] args) throws Exception {
        ProtocoloTexto protocolo = new ProtocoloTexto();
        for (String mensaje : new String[]{"CONSULTAR pan", "CONSULTAR leche", "OTRO pan"})
            System.out.println(protocolo.procesar(mensaje).codificar());
    }
}

// Salida esperada:
// 200 12
// 404 No encontrado
// 400 Sintaxis invalida
