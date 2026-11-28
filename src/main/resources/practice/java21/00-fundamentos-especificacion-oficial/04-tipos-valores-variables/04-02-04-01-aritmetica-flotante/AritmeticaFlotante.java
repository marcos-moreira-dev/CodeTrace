// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.2.4; ejemplo 4.2.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . AritmeticaFlotante.java
// Ejecutar: java AritmeticaFlotante
// Comandos desde la carpeta de la unidad.

public class AritmeticaFlotante {
    public static void main(String[] args) {
        double grande = Double.MAX_VALUE;
        System.out.println(grande * 2);
        double pequeno = Double.MIN_NORMAL;
        System.out.println(pequeno / 2 > 0);
        System.out.println(Double.MIN_VALUE / 2);
        System.out.println(0.0 / 0.0);
        System.out.println(0.1 + 0.2 == 0.3);
        System.out.println(1.0 / -0.0);
    }
}

// Salida esperada:
// Infinity
// true
// 0.0
// NaN
// false
// -Infinity
