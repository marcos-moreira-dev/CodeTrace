public final class Strategy {
    interface Descuento {
        double aplicar(double total);
    }

    static double cobrar(double total, Descuento estrategia) {
        return estrategia.aplicar(total);
    }

    public static void main(String[] args) {
        Descuento clienteFiel = total -> total * 0.90;
        System.out.println(cobrar(50, clienteFiel));
    }
}
