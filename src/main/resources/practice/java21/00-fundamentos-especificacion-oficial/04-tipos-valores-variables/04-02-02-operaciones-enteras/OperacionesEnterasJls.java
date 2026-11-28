// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §4.2.2; ejemplo 4.2.2-1
// Compilar: javac --release 21 -encoding UTF-8 OperacionesEnterasJls.java
// Ejecutar: java OperacionesEnterasJls
// La multiplicación int puede desbordarse; long permite representar este producto. La división por cero entero lanza una excepción.

public class OperacionesEnterasJls {
    public static void main(String[] args) {
        int lado = 50_000;
        System.out.println(lado * lado);

        long ladoLargo = lado;
        System.out.println(ladoLargo * ladoLargo);

        System.out.println(100 / (ladoLargo - lado));
    }
}

// Salida esperada:
// -1794967296
// 2500000000
// Después: ArithmeticException (intencional).
