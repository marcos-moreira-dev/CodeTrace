// Tema:
// Una sobrescritura puede ampliar el comportamiento heredado mediante super. El método de
// escritura reutiliza el buffer y añade el vaciado al encontrar un salto de línea.
// Compilar: javac --release 21 -encoding UTF-8 -d . Buffer.java BufferLinea.java BufferPorLinea.java
// Ejecutar: java BufferPorLinea
// Comandos desde la carpeta de la unidad.

class Buffer {
    StringBuilder datos = new StringBuilder();
    void agregar(char c) {
        if (datos.length() == 16) vaciar();
        datos.append(c);
    }
    void escribir(String s) { for (char c : s.toCharArray()) agregar(c); }
    void vaciar() { System.out.print(datos); datos.setLength(0); }
}

// Salida esperada:
// uno
// intermedio
// dos
