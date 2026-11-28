// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.7.4; ejemplo 15.7.4-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ArgumentoFallido.java
// Ejecutar: java ArgumentoFallido
// Comandos desde la carpeta de la unidad.

public class ArgumentoFallido {
    static void mostrar(int a, int b, int c) {}
    static int fallo() { throw new IllegalStateException(); }
    public static void main(String[] args) {
        int n = 0;
        try { mostrar(n = 2, fallo(), n = 7); }
        catch (IllegalStateException e) { System.out.println(n); }
    }
}

// Salida esperada:
// 2
