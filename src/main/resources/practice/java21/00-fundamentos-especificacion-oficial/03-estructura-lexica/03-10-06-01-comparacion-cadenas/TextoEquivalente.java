// Tema:
// Un bloque de texto y una cadena ordinaria pueden representar exactamente los mismos
// caracteres. equals compara ese contenido, incluidos espacios y saltos de línea.
// Compilar: javac --release 21 -encoding UTF-8 -d . TextoEquivalente.java
// Ejecutar: java TextoEquivalente
// Comandos desde la carpeta de la unidad.

public class TextoEquivalente {
    public static void main(String[] args) {
        String normal = "inicio\n    dato\nfin\n";
        String bloque = """
                inicio
                    dato
                fin
                """;
        System.out.print(bloque);
        System.out.println(normal.equals(bloque));
    }
}

// Salida esperada:
// inicio
//     dato
// fin
// true
