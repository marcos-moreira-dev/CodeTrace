// Tema:
// Una sobrescritura puede ampliar el comportamiento heredado mediante super. El método de
// escritura reutiliza el buffer y añade el vaciado al encontrar un salto de línea.
// Compilar: javac --release 21 -encoding UTF-8 -d . Buffer.java BufferLinea.java BufferPorLinea.java
// Ejecutar: java BufferPorLinea
// Comandos desde la carpeta de la unidad.

class BufferPorLinea {
    public static void main(String[] args) {
        BufferLinea b = new BufferLinea();
        b.escribir("uno\ndos");
        System.out.println("intermedio");
        b.escribir("\n");
    }
}

// Salida esperada:
// uno
// intermedio
// dos
