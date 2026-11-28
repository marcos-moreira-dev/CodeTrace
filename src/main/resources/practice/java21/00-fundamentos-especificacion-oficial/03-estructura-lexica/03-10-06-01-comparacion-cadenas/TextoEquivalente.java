// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §3.10.6; ejemplo 3.10.6-1
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
