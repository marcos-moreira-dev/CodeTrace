// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §5.1.3; ejemplo 5.1.3-2
// Compilar: javac --release 21 -encoding UTF-8 ReduccionPrimitivaJls.java
// Ejecutar: java ReduccionPrimitivaJls
// Tema JLS:
// La reducción entre enteros conserva los bits inferiores. Al convertir un valor flotante a
// int o long se trunca hacia cero; fuera del rango se satura y NaN se convierte en cero.
// Reducir double a float también puede producir infinito o cero.
// Una reducción puede perder bits, truncar o saturar según los tipos involucrados.

public class ReduccionPrimitivaJls {
    public static void main(String[] args) {
        System.out.println((short) 0x1234ABCD);
        System.out.println((byte) 200);
        System.out.println((int) 1e30);
        System.out.println((int) Double.NaN);
        System.out.println((float) -1e90);
        System.out.println((float) 1e-60);
    }
}

// Salida esperada:
// -21555
// -56
// 2147483647
// 0
// -Infinity
// 0.0
