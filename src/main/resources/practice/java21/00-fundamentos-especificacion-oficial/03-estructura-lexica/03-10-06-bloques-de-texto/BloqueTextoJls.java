// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §3.10.6
// Compilar: javac --release 21 -encoding UTF-8 BloqueTextoJls.java
// Ejecutar: java BloqueTextoJls
// El bloque de texto elimina la sangría incidental y conserva saltos de línea.

public class BloqueTextoJls {
    public static void main(String[] args) {
        String texto = """
                uno
                dos
                """;
        System.out.print(texto);
    }
}

// Salida esperada:
// uno
// dos
