// Tema:
// Reducir entre enteros descarta bits superiores. Al convertir flotantes a int o long se
// trunca hacia cero, se satura fuera de rango y NaN se convierte en cero.
// Compilar: javac --release 21 -encoding UTF-8 ReduccionPrimitivaJls.java
// Ejecutar: java ReduccionPrimitivaJls

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
