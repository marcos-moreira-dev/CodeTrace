public final class Callbacks {
    @FunctionalInterface
    interface Receptor {
        void recibir(String mensaje);
    }

    private Callbacks() {}

    static void notificar(String texto, Receptor receptor) {
        receptor.recibir(texto);
    }

    static void mostrar(String texto) {
        System.out.println(texto);
    }

    public static void main(String[] args) {
        notificar("Hola", Callbacks::mostrar);

        Runnable tarea = () -> mostrar("Lista");
        tarea.run();
    }
}
