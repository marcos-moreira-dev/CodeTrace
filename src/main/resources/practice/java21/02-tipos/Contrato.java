public interface Contrato {
    double calcular(double subtotal);

    default boolean aplica(double subtotal) {
        return subtotal > 0;
    }
}
