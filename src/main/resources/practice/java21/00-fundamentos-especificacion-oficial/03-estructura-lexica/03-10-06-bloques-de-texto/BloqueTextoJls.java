// Tema:
// Un bloque de texto produce un String. Se normalizan los finales de línea, se elimina la
// sangría incidental y finalmente se interpretan los escapes. La posición del delimitador de
// cierre influye en la sangría y el salto final.
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
