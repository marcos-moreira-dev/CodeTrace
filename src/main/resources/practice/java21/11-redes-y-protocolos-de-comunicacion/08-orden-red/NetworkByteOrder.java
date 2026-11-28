// Tema:
// Los campos multibyte de numerosos protocolos se codifican en big-endian: primero el octeto más significativo. ByteBuffer permite declarar ese orden.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 NetworkByteOrder.java
// Ejecutar: java NetworkByteOrder

import java.nio.*;

public class NetworkByteOrder {
    public static void main(String[] args) throws Exception {
        ByteBuffer b = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN);
        b.putInt(0x01020304);
        for (byte dato : b.array()) System.out.printf("%02x ", dato & 255);
        System.out.println();
    }
}

// Salida esperada:
// 01 02 03 04
