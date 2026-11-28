// Tema:
// Una asignación compuesta necesita leer el componente antes de evaluar el lado derecho. Por
// ello comprueba null y límites antes de los efectos del operando derecho.
// Compilar: javac --release 21 -encoding UTF-8 -d . OrdenAsignacionCompuesta.java
// Ejecutar: java OrdenAsignacionCompuesta
// Comandos desde la carpeta de la unidad.

class OrdenAsignacionCompuesta {
    static int derecha() { System.out.println("derecha"); return 9; }
    static int indice() { System.out.println("índice"); return 3; }
    public static void main(String[] args) {
        int[] a = null;
        try { a[indice()] += derecha(); }
        catch (NullPointerException e) { System.out.println("null"); }
        a = new int[1];
        try { a[indice()] += derecha(); }
        catch (ArrayIndexOutOfBoundsException e) { System.out.println("rango"); }
    }
}

// Salida esperada:
// índice
// null
// índice
// rango
