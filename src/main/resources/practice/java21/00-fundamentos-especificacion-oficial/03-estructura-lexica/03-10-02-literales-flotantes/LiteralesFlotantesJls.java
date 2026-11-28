// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §3.10.2
// Compilar: javac --release 21 -encoding UTF-8 LiteralesFlotantesJls.java
// Ejecutar: java LiteralesFlotantesJls
// Tema JLS:
// Un literal de punto flotante es double salvo que lleve f o F. e indica un exponente
// decimal y p uno binario en la notación hexadecimal. El tipo determina la precisión con
// que se representa el valor.
// El sufijo f selecciona float; sin sufijo, un literal flotante es double.

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
