// Tema:
// compact conserva los bytes no consumidos y prepara el buffer para seguir escribiendo.
// clear reinicia posición y límite, pero no borra físicamente los datos.
// Modalidad: R (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 FlipClearCompact.java
// Ejecutar: java FlipClearCompact

import java.nio.ByteBuffer;

public class FlipClearCompact {
    public static void main(String[] args) throws Exception {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.put(new byte[]{1, 2, 3});
        b.flip();
        System.out.println(b.get());
        b.compact();
        b.put((byte) 4);
        b.flip();
        while (b.hasRemaining()) System.out.println(b.get());
        b.clear();
        System.out.println(b.position() + "/" + b.limit());
    }
}

// Salida esperada:
// 1
// 2
// 3
// 4
// 0/4
