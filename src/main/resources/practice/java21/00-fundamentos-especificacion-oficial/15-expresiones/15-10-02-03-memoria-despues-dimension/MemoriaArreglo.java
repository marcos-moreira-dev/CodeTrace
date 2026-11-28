// Tema:
// Las dimensiones de un arreglo se evalúan antes de intentar reservar memoria. Incluso si la
// reserva falla, los efectos de esas expresiones ya ocurrieron. Usar el límite de heap
// indicado.
// Compilar: javac --release 21 -encoding UTF-8 -d . MemoriaArreglo.java
// Ejecutar: java -Xmx16m MemoriaArreglo
// Comandos desde la carpeta de la unidad.

class MemoriaArreglo {
    public static void main(String[] args) {
        int dimension = 0;
        try {
            // Una dimensión mayor que la capacidad de un heap de 16 MiB.
            int[] datos = new int[dimension = Integer.MAX_VALUE];
            System.out.println(datos.length);
        } catch (OutOfMemoryError e) {
            System.out.println(dimension == Integer.MAX_VALUE);
        }
    }
}

// Salida esperada:
// true
