// Tema:
// Una invocación genérica puede usar el tipo objetivo para deducir un parámetro de tipo que
// no aparece en los argumentos. En este caso la asignación a List<String> determina T en la
// invocación de vacia.
// Compilar: javac --release 21 -encoding UTF-8 -d . InferenciaObjetivo.java
// Ejecutar: java InferenciaObjetivo
// Comandos desde la carpeta de la unidad.

import java.util.ArrayList;
import java.util.List;

public class InferenciaObjetivo {
    static <T> List<T> vacia() {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        List<String> textos = vacia();
        textos.add("Java");
        System.out.println(textos.get(0).length());
    }
}

// Salida esperada:
// 4
