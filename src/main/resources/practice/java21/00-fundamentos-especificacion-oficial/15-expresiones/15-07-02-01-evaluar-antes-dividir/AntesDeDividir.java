// Tema:
// Ambos operandos se evalúan antes de realizar la división. Si evaluar uno provoca una
// excepción, esa excepción precede a la comprobación de división por cero.
// Compilar: javac --release 21 -encoding UTF-8 -d . AntesDeDividir.java
// Ejecutar: java AntesDeDividir
// Comandos desde la carpeta de la unidad.

public class AntesDeDividir {
    static int fallo() { throw new IllegalStateException("operando"); }
    public static void main(String[] args) {
        int cero = 0;
        try { int resultado = 10 / (cero * fallo()); }
        catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }
}

// Salida esperada:
// operando
