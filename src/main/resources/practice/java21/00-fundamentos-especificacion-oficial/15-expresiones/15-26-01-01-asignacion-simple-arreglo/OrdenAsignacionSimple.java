// Tema:
// En una asignación simple a un componente se evalúan referencia, índice y lado derecho antes
// de ciertas comprobaciones de almacenamiento. El lado derecho puede ejecutarse antes del
// fallo por null o índice inválido.
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
