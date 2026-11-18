public final class Euclides {
    private Euclides() {}

    public static int mcd(int a, int b) {
        if (a < 0 || b < 0) throw new IllegalArgumentException("Usa enteros no negativos");
        while (b != 0) {
            int resto = a % b;
            a = b;
            b = resto;
        }
        return a;
    }
}
