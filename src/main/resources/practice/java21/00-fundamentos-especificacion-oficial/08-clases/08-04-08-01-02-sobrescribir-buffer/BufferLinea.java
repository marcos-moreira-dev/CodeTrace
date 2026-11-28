// Tema:
// Una sobrescritura puede ampliar el comportamiento heredado mediante super. El método de
// escritura reutiliza el buffer y añade el vaciado al encontrar un salto de línea.
// Compilar: javac --release 21 -encoding UTF-8 -d . Buffer.java BufferLinea.java BufferPorLinea.java
// Ejecutar: java BufferPorLinea
// Comandos desde la carpeta de la unidad.

class BufferLinea extends Buffer {
    @Override void agregar(char c) {
        super.agregar(c);
        if (c == '\n') vaciar();
    }
}

// Salida esperada:
// uno
// intermedio
// dos
