// Tema:
// En ocho bits, el complemento a dos de un valor se obtiene invirtiendo los bits y sumando
// uno. La máscara 255 muestra solamente ocho bits.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 ComplementoDos.java
// Ejecutar: java ComplementoDos

public class ComplementoDos {
    public static void main(String[] args) {
        int positivo = 5;
        int negativo = (~positivo + 1) & 255;
        System.out.println(Integer.toBinaryString(negativo));
        System.out.println((byte) negativo);
    }
}

// Salida esperada:
// 11111011
// -5
