// Tema:
// List<?> contiene elementos de un tipo desconocido. Un método genérico auxiliar permite
// dar un nombre T a ese tipo capturado. Así puede intercambiar dos elementos de la misma
// lista sin un cast ni insertar objetos ajenos.
// Compilar: javac --release 21 -encoding UTF-8 -d . CapturaComodin.java
// Ejecutar: java CapturaComodin
// Comandos desde la carpeta de la unidad.

import java.util.ArrayList;
import java.util.List;

public class CapturaComodin {
    static void intercambiar(List<?> lista) {
        intercambiarCaptura(lista);
    }

    private static <T> void intercambiarCaptura(List<T> lista) {
        T primero = lista.get(0);
        lista.set(0, lista.get(1));
        lista.set(1, primero);
    }

    public static void main(String[] args) {
        var lista = new ArrayList<>(List.of("sol", "luna"));
        intercambiar(lista);
        System.out.println(lista);
    }
}

// Salida esperada:
// [luna, sol]
