// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §3.10.5
// Compilar: javac --release 21 -encoding UTF-8 LiteralesCadenaJls.java
// Ejecutar: java LiteralesCadenaJls
// Los literales de cadena y las expresiones constantes equivalentes se internan.

public class LiteralesCadenaJls {
    public static void main(String[] args) {
        String a = "casa";
        String b = "ca" + "sa";
        String parte = "ca";
        String c = parte + "sa";
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a.equals(c));
    }
}

// Salida esperada:
// true
// false
// true
