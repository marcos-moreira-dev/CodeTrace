// Tema:
// Los bloques de texto normalizan los finales de línea antes de interpretar los escapes. Un
// retorno de carro escrito mediante un escape sobrevive a esa normalización.
// Compilar: javac --release 21 -encoding UTF-8 -d . TransformacionTexto.java
// Ejecutar: java TransformacionTexto
// Comandos desde la carpeta de la unidad.

public class TransformacionTexto {
    public static void main(String[] args) {
        // Los escapes se interpretan después de quitar la sangría.
        String texto = """
                x\r
                y\r
                """;
        System.out.println(texto.replace("\r", "CR").replace("\n", "LF"));
    }
}

// Salida esperada:
// xCRLFyCRLF
