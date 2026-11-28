// Tema:
// position indica el próximo índice de transferencia; limit es el límite exclusivo.
// capacity no cambia. flip prepara los bytes escritos para lectura.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 EstadoByteBuffer.java
// Ejecutar: java EstadoByteBuffer

import java.nio.ByteBuffer;

public class EstadoByteBuffer {
    public static void main(String[] args) throws Exception {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.put((byte) 10);
        b.put((byte) 20);
        System.out.println(b.position() + "/" + b.limit() + "/" + b.capacity());
        b.flip();
        System.out.println(b.position() + "/" + b.limit() + "/" + b.capacity());
        System.out.println(b.get());
    }
}

// Salida esperada:
// 2/4/4
// 0/2/4
// 10
