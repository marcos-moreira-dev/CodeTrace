// Tema:
// Una multiplicación de operandos int se calcula como int y puede desbordarse. Convertir un
// operando a long antes de multiplicar cambia el tipo de la operación. Dividir un entero por
// cero provoca ArithmeticException.
// Compilar: javac --release 21 -encoding UTF-8 OperacionesEnterasJls.java
// Ejecutar: java OperacionesEnterasJls

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
