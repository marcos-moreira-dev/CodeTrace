// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.10.2; ejemplo 15.10.2-3
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
