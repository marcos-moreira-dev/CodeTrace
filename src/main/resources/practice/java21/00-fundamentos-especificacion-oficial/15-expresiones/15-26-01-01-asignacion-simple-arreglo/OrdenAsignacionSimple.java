// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.26.1; ejemplo 15.26.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . OrdenAsignacionSimple.java
// Ejecutar: java OrdenAsignacionSimple
// Comandos desde la carpeta de la unidad.

class OrdenAsignacionSimple {
    static int derecha() { System.out.println("derecha"); return 9; }
    static int indice() { System.out.println("índice"); return 3; }
    public static void main(String[] args) {
        int[] a = null;
        try { a[indice()] = derecha(); }
        catch (NullPointerException e) { System.out.println("null"); }
        a = new int[1];
        try { a[indice()] = derecha(); }
        catch (ArrayIndexOutOfBoundsException e) { System.out.println("rango"); }
    }
}

// Salida esperada:
// índice
// derecha
// null
// índice
// derecha
// rango
