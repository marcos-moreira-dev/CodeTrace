// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.1.3; ejemplo 5.1.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . InfinitosEnteros.java
// Ejecutar: java InfinitosEnteros
// Comandos desde la carpeta de la unidad.

public class InfinitosEnteros {
    public static void main(String[] args) {
        double minimo = Double.NEGATIVE_INFINITY;
        double maximo = Double.POSITIVE_INFINITY;
        System.out.println((long) minimo + " " + (long) maximo);
        System.out.println((int) minimo + " " + (int) maximo);
        System.out.println((short) minimo + " " + (short) maximo);
        System.out.println((int) (char) minimo + " " + (int) (char) maximo);
        System.out.println((byte) minimo + " " + (byte) maximo);
    }
}

// Salida esperada:
// -9223372036854775808 9223372036854775807
// -2147483648 2147483647
// 0 -1
// 0 65535
// 0 -1
