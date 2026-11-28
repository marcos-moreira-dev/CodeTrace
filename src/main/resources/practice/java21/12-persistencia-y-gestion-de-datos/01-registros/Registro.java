// Tema:
// Un registro de tamaño fijo permite localizar la fila i con i * tamaño. Se guardan identificador y cantidad como dos int de cuatro bytes, en big-endian. El archivo temporal se borra al terminar.
// Modalidad: ejecución real con archivos locales.
// Compilar: javac --release 21 -encoding UTF-8 Registro.java ArchivoRegistros.java MainRegistros.java
// Ejecutar: java MainRegistros

public record Registro(int id, int cantidad) {
    public static final int BYTES = 8;
}

// Salida esperada:
// Bytes: 16
// ID: 102, cantidad: 7
