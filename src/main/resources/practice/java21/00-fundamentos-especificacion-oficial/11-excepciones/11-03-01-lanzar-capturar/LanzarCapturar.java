// Tema:
// Una excepción interrumpe el flujo normal y se propaga hasta un catch compatible. Después de
// manejarla, la ejecución puede continuar tras el bloque try-catch.
// Compilar: javac --release 21 -encoding UTF-8 -d . LanzarCapturar.java
// Ejecutar: java LanzarCapturar
// Comandos desde la carpeta de la unidad.

public class LanzarCapturar {
    static void calcular() {
        int cero = 0;
        System.out.println(3 / cero);
    }
    public static void main(String[] args) {
        try { calcular(); }
        catch (ArithmeticException e) { System.out.println("capturada"); }
        System.out.println("continúa");
    }
}

// Salida esperada:
// capturada
// continúa
