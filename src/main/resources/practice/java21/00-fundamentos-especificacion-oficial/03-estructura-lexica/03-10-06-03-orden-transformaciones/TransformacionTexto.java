// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §3.10.6; ejemplo 3.10.6-3
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
