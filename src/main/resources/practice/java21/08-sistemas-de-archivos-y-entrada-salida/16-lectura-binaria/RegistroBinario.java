// Tema:
// Formato binario fijo: identificador int de 4 bytes, cantidad int de 4 bytes, ambos big-endian.
// La lectura reproduce exactamente el orden de la escritura; no es serialización de objetos Java.
// Modalidad: R.
// Compilar: javac --release 21 -encoding UTF-8 RegistroBinario.java MainLecturaRegistro.java
// Ejecutar: java MainLecturaRegistro

import java.io.*;

public record RegistroBinario(int id, int cantidad) {
    public void escribir(DataOutputStream salida) throws IOException {
        salida.writeInt(id);
        salida.writeInt(cantidad);
    }

    public static RegistroBinario leer(DataInputStream entrada) throws IOException {
        return new RegistroBinario(entrada.readInt(), entrada.readInt());
    }
}

// Salida esperada:
// 17: 3
// Bytes: 8
