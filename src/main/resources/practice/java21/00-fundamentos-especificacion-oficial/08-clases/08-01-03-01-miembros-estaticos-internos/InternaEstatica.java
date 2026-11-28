// Tema:
// Una clase interna puede declarar miembros static. Estos pertenecen a la clase interna, no a
// una instancia exterior; su inicialización ocurre al inicializar el tipo correspondiente.
// Compilar: javac --release 21 -encoding UTF-8 -d . InternaEstatica.java
// Ejecutar: java InternaEstatica
// Comandos desde la carpeta de la unidad.

public class InternaEstatica {
    class Interior {
        static int numero = 8;
        static { System.out.println("inicializada"); }
        static int leer() { return numero; }
        static class Anidada {}
    }
    public static void main(String[] args) {
        System.out.println(Interior.leer());
    }
}

// Salida esperada:
// inicializada
// 8
