// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA de la carpeta java21:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\03-funciones\Callbacks.java'
// Ejecutar: java -cp "$env:TEMP\codetrace-java" Callbacks

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
