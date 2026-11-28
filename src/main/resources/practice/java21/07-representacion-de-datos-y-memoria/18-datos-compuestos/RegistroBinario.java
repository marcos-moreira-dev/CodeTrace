// Tema:
// El formato binario explícito tiene seis bytes: cuatro para el identificador y dos para la
// cantidad, en big endian. No describe la disposición física de un objeto Java.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 RegistroBinario.java MainRegistroBinario.java
// Ejecutar: java MainRegistroBinario

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class RegistroBinario {
    final int id;
    final short cantidad;

    RegistroBinario(int id, short cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    byte[] codificar() {
        return ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN)
                .putInt(id).putShort(cantidad).array();
    }

    static RegistroBinario decodificar(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
        return new RegistroBinario(buffer.getInt(), buffer.getShort());
    }
}

// Salida esperada:
// 0000002a0007
// 42 7
