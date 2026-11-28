// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.1; ejemplo 8.4.8.1-2
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
