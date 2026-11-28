// Tema:
// Los métodos abstractos equivalentes a métodos públicos de Object no cuentan para
// determinar si una interfaz es funcional. La interfaz puede declarar equals y además un
// único método abstracto propio.
// Compilar: javac --release 21 -encoding UTF-8 -d . FuncionalConEquals.java
// Ejecutar: java FuncionalConEquals

public class FuncionalConEquals {
    @FunctionalInterface
    interface Comparar {
        boolean equals(Object otro);
        int comparar(String a, String b);
    }
    public static void main(String[] args) {
        Comparar c = String::compareTo;
        System.out.println(c.comparar("a", "b") < 0);
    }
}

// Salida esperada:
// true
