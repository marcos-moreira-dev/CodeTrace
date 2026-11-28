// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §3.10.6; ejemplo 3.10.6-2
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
