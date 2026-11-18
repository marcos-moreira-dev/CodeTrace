public class Excepciones {
    public static int dividir(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisor cero");
        }
        return a / b;
    }

    public static void main(String[] args) {
        try {
            System.out.println(dividir(10, 0));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
