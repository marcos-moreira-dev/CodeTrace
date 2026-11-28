// Tema:
// Los literales String y las concatenaciones constantes se internan. Una concatenación
// calculada en ejecución puede tener el mismo contenido y una referencia diferente: == compara
// referencias y equals compara contenido.
// Compilar: javac --release 21 -encoding UTF-8 LiteralesCadenaJls.java
// Ejecutar: java LiteralesCadenaJls

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
