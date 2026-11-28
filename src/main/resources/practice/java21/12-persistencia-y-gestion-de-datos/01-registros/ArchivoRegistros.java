// Tema:
// Un registro de tamaño fijo permite localizar la fila i con i * tamaño. Se guardan identificador y cantidad como dos int de cuatro bytes, en big-endian. El archivo temporal se borra al terminar.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 Registro.java ArchivoRegistros.java MainRegistros.java
// Ejecutar: java MainRegistros

import java.io.*;
import java.nio.file.Path;

public class ArchivoRegistros implements AutoCloseable {
    private final RandomAccessFile archivo;

    public ArchivoRegistros(Path ruta) throws IOException {
        archivo = new RandomAccessFile(ruta.toFile(), "rw");
    }
    public void anexar(Registro r) throws IOException {
        archivo.seek(archivo.length());
        archivo.writeInt(r.id());
        archivo.writeInt(r.cantidad());
    }
    public Registro leer(int indice) throws IOException {
        if (indice < 0 || (long) indice >= archivo.length() / Registro.BYTES)
            throw new IndexOutOfBoundsException(indice);
        archivo.seek((long) indice * Registro.BYTES);
        return new Registro(archivo.readInt(), archivo.readInt());
    }
    public long bytes() throws IOException { return archivo.length(); }
    public void close() throws IOException { archivo.close(); }
}

// Salida esperada:
// Bytes: 16
// ID: 102, cantidad: 7
