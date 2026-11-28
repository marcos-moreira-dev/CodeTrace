// Tema:
// Un tipo raw omite los argumentos genéricos. Permite ciertas operaciones unchecked y puede
// contaminar una colección parametrizada; una lectura posterior puede provocar
// ClassCastException.
// Compilar: javac --release 21 -encoding UTF-8 -d . TipoRaw.java
// Ejecutar: java TipoRaw
// Comandos desde la carpeta de la unidad.

import java.util.ArrayList;
import java.util.List;
public class TipoRaw {
    public static void main(String[] args) {
        List<String> palabras = new ArrayList<>();
        List sinParametro = palabras;
        sinParametro.add(9); // Advertencia unchecked: se pierde seguridad.
        System.out.println(palabras.get(0).length());
    }
}

// Salida esperada:
// (sin salida estándar antes de la excepción)
// Después: ClassCastException (intencional).
