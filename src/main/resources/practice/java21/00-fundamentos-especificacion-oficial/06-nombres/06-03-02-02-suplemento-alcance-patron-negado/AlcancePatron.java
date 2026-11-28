// Tema:
// Una variable de patrón existe donde se garantiza que el patrón coincidió. Si la condición
// negada termina con return, las instrucciones posteriores solo pueden alcanzarse cuando la
// referencia es String.
// Compilar: javac --release 21 -encoding UTF-8 -d . AlcancePatron.java
// Ejecutar: java AlcancePatron
// Comandos desde la carpeta de la unidad.

public class AlcancePatron {
    static void mostrar(Object dato) {
        if (!(dato instanceof String texto)) {
            return;
        }
        System.out.println(texto.length());
    }

    public static void main(String[] args) {
        mostrar(42);
        mostrar("Java");
    }
}

// Salida esperada:
// 4
