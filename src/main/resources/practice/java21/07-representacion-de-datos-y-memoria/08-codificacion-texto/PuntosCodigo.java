// Tema:
// String.length cuenta unidades UTF-16. Un punto de código suplementario utiliza dos
// unidades; avanzar por puntos de código requiere Character.charCount.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 PuntosCodigo.java
// Ejecutar: java PuntosCodigo

public class PuntosCodigo {
    public static void main(String[] args) {
        String texto = "A😀";
        System.out.println(texto.length());
        System.out.println(texto.codePointCount(0, texto.length()));
        for (int i = 0; i < texto.length();) {
            int punto = texto.codePointAt(i);
            System.out.println(Integer.toHexString(punto));
            i += Character.charCount(punto);
        }
    }
}

// Salida esperada:
// 3
// 2
// 41
// 1f600
