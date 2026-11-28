// Tema:
// Un literal flotante es double salvo que lleve f o F. e indica un exponente decimal; p
// expresa un exponente binario en notación hexadecimal.
// Compilar: javac --release 21 -encoding UTF-8 LiteralesFlotantesJls.java
// Ejecutar: java LiteralesFlotantesJls

public class LiteralesFlotantesJls {
    public static void main(String[] args) {
        float a = 1.25f;
        double b = 1.25e2;
        double c = 0x1.8p2;
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
    }
}

// Salida esperada:
// 1.25
// 125.0
// 6.0
