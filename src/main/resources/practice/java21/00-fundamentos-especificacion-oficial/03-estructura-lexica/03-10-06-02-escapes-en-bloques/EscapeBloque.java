// Tema:
// El escape de espacio explícito preserva espacios finales; una barra invertida seguida de un
// fin de línea suprime ese salto dentro del bloque de texto.
// Compilar: javac --release 21 -encoding UTF-8 -d . EscapeBloque.java
// Ejecutar: java EscapeBloque
// Comandos desde la carpeta de la unidad.

public class EscapeBloque {
    public static void main(String[] args) {
        String texto = """
                norte\s
                sur\
                este
                """;
        // El espacio explícito se conserva y la barra une las dos líneas.
        System.out.println(texto.replace(" ", "_").replace("\n", "|"));
    }
}

// Salida esperada:
// norte_|sureste|
