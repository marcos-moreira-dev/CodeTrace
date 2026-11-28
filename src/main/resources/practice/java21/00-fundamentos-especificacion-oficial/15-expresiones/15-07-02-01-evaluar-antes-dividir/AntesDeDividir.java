// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.7.2; ejemplo 15.7.2-1
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
