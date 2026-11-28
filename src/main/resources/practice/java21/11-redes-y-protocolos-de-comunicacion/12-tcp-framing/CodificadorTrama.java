// Tema:
// Framing: un entero de cuatro bytes anuncia la longitud de la carga UTF-8. readFully reconstruye el mensaje aunque TCP entregue fragmentos; la longitud se valida antes de reservar memoria.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 CodificadorTrama.java Servidor.java Cliente.java MainFraming.java
// Ejecutar: java MainFraming

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CodificadorTrama {
    private static final int MAXIMO = 1024;
    public static void escribir(DataOutputStream salida, String mensaje) throws IOException {
        byte[] datos = mensaje.getBytes(StandardCharsets.UTF_8);
        if (datos.length > MAXIMO) throw new IOException("Mensaje demasiado largo");
        salida.writeInt(datos.length);
        salida.write(datos);
        salida.flush();
    }
    public static String leer(DataInputStream entrada) throws IOException {
        int longitud = entrada.readInt();
        if (longitud < 0 || longitud > MAXIMO) throw new IOException("Longitud invalida");
        byte[] datos = new byte[longitud];
        entrada.readFully(datos);
        return new String(datos, StandardCharsets.UTF_8);
    }
}

// Salida esperada:
// OK pan
// OK leche
