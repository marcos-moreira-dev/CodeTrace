// Tema:
// Un registro de tamaño fijo permite localizar la fila i con i * tamaño. Se guardan identificador y cantidad como dos int de cuatro bytes, en big-endian. El archivo temporal se borra al terminar.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 Registro.java ArchivoRegistros.java MainRegistros.java
// Ejecutar: java MainRegistros

import java.nio.file.*;

public class MainRegistros {
    public static void main(String[] args) throws Exception {
        Path ruta = Files.createTempFile("registros-", ".bin");
        try {
            try (ArchivoRegistros archivo = new ArchivoRegistros(ruta)) {
                archivo.anexar(new Registro(101, 12));
                archivo.anexar(new Registro(102, 7));
                System.out.println("Bytes: " + archivo.bytes());
            }
            // Una nueva apertura lee lo que dejó la anterior, no objetos de memoria.
            try (ArchivoRegistros archivo = new ArchivoRegistros(ruta)) {
                Registro r = archivo.leer(1);
                System.out.println("ID: " + r.id() + ", cantidad: " + r.cantidad());
            }
        } finally { Files.deleteIfExists(ruta); }
    }
}

// Salida esperada:
// Bytes: 16
// ID: 102, cantidad: 7
