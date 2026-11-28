// Tema:
// El orden de bytes determina cómo se almacenan los bytes de un valor multibyte. No cambia
// el orden de los bits dentro de cada byte.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 LittleBigEndian.java
// Ejecutar: java LittleBigEndian

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HexFormat;

public class LittleBigEndian {
    public static void main(String[] args) {
        int dato = 0x12345678;
        ByteBuffer grande = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        ByteBuffer pequeno = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        grande.putInt(dato);
        pequeno.putInt(dato);
        System.out.println(HexFormat.of().formatHex(grande.array()));
        System.out.println(HexFormat.of().formatHex(pequeno.array()));
    }
}

// Salida esperada:
// 12345678
// 78563412
