// Tema:
// Un contrato establece qué exige un método (precondición) y qué garantiza (postcondición). Validamos antes de calcular y comprobamos el resultado sin depender de -ea.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 PrePostcondiciones.java
// Ejecutar: java PrePostcondiciones

public class PrePostcondiciones {
    static int total(int cantidad, int centavos) {
        if (cantidad < 0 || centavos < 0) throw new IllegalArgumentException("Entrada");
        int total = Math.multiplyExact(cantidad, centavos);
        if (total < 0) throw new AssertionError("Postcondicion");
        return total;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Precio: " + total(3, 250));
        try { total(-1, 250); }
        catch (IllegalArgumentException e) { System.out.println("Entrada rechazada"); }
    }
}

// Salida esperada:
// Precio: 750
// Entrada rechazada
