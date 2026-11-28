// Tema:
// Probar límites incluye vacío, mínimo, máximo y valores inválidos. Se verifica un contrato de búsqueda de máximo sin usar un valor centinela ambiguo.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 PruebaCasosLimite.java
// Ejecutar: java PruebaCasosLimite

import java.util.OptionalInt;

public class PruebaCasosLimite {
    static OptionalInt maximo(int[] datos) {
        if (datos == null) throw new IllegalArgumentException("Datos");
        if (datos.length == 0) return OptionalInt.empty();
        int max = datos[0];
        for (int dato : datos) max = Math.max(max, dato);
        return OptionalInt.of(max);
    }
    static void igual(OptionalInt a, OptionalInt b) {
        if (!a.equals(b)) throw new AssertionError(a + " != " + b);
    }

    public static void main(String[] args) throws Exception {
        igual(OptionalInt.empty(), maximo(new int[]{}));
        igual(OptionalInt.of(Integer.MIN_VALUE), maximo(new int[]{Integer.MIN_VALUE}));
        igual(OptionalInt.of(Integer.MAX_VALUE), maximo(new int[]{-1, Integer.MAX_VALUE}));
        try { maximo(null); throw new AssertionError("Se esperaba error"); }
        catch (IllegalArgumentException esperado) { }
        System.out.println("Limites: 4 OK");
    }
}

// Salida esperada:
// Limites: 4 OK
