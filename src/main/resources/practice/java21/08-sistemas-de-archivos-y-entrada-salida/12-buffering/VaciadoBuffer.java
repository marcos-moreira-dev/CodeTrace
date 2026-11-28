// Tema:
// flush entrega los datos pendientes al flujo subyacente; no garantiza persistencia física.
// El flujo en memoria muestra la diferencia sin depender del disco.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 VaciadoBuffer.java
// Ejecutar: java VaciadoBuffer

import java.io.*;

public class VaciadoBuffer {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream destino = new ByteArrayOutputStream();
        try (BufferedOutputStream salida = new BufferedOutputStream(destino, 8)) {
            salida.write(new byte[]{1, 2, 3});
            System.out.println("Antes: " + destino.size());
            salida.flush();
            System.out.println("Despues: " + destino.size());
        }
    }
}

// Salida esperada:
// Antes: 0
// Despues: 3
