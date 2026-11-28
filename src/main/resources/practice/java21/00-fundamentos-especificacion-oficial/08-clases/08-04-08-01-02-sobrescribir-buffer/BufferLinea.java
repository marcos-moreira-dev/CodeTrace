// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.1; ejemplo 8.4.8.1-2
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
