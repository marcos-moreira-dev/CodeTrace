// Tema:
// java.util.logging distingue niveles y permite elegir el destino. Un StreamHandler con Formatter propio evita fechas y detalles variables en la prueba. Se desactiva el handler padre para no duplicar registros.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 RegistroJul.java
// Ejecutar: java RegistroJul

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;

public class RegistroJul {
    public static void main(String[] args) throws Exception {
        Logger log = Logger.getLogger("practica.registro");
        log.setUseParentHandlers(false);
        log.setLevel(Level.INFO);
        ByteArrayOutputStream destino = new ByteArrayOutputStream();
        StreamHandler handler = new StreamHandler(destino, new Formatter() {
            public String format(LogRecord r) { return r.getLevel().getName() + ": " + r.getMessage() + "\n"; }
        });
        handler.setEncoding("UTF-8");
        handler.setLevel(Level.INFO);
        log.addHandler(handler);
        try {
            log.fine("Detalle");
            log.info("Pedido recibido");
            log.warning("Stock bajo");
            handler.flush();
            System.out.print(destino.toString(StandardCharsets.UTF_8));
        } finally { log.removeHandler(handler); handler.close(); }
    }
}

// Salida esperada:
// INFO: Pedido recibido
// WARNING: Stock bajo
