// Tema:
// Un índice en memoria asocia clave a desplazamiento en bytes. Se construye mediante un recorrido inicial y después permite acceso directo. Se restringe a registros fijos de dos int y claves únicas; no es un índice persistente ni B-tree.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 IndiceArchivo.java MainIndice.java
// Ejecutar: java MainIndice

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.OptionalInt;

public class IndiceArchivo implements AutoCloseable {
    private final RandomAccessFile archivo;
    private final HashMap<Integer, Long> posiciones = new HashMap<>();

    public IndiceArchivo(Path ruta) throws IOException {
        archivo = new RandomAccessFile(ruta.toFile(), "r");
        try {
            if (archivo.length() % 8 != 0) throw new IOException("Registro truncado");
            while (archivo.getFilePointer() < archivo.length()) {
                long offset = archivo.getFilePointer();
                int id = archivo.readInt();
                archivo.readInt();
                if (posiciones.putIfAbsent(id, offset) != null) throw new IOException("Clave duplicada");
            }
        } catch (IOException | RuntimeException e) {
            archivo.close();
            throw e;
        }
    }

    public OptionalInt buscar(int id) throws IOException {
        Long offset = posiciones.get(id);
        if (offset == null) return OptionalInt.empty();
        archivo.seek(offset);
        if (archivo.readInt() != id) throw new IOException("Indice desactualizado");
        return OptionalInt.of(archivo.readInt());
    }

    public void close() throws IOException { archivo.close(); }
}

// Salida esperada:
// 102: 7
// 999 existe: false
