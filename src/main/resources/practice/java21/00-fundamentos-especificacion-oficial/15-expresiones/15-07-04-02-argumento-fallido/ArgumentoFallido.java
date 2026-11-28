// Tema:
// Cuando evaluar un argumento termina abruptamente, los argumentos posteriores no se evalúan y
// la invocación no se completa.
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
