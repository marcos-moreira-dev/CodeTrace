// Tema:
// El desplazamiento >> replica el signo; >>> rellena con ceros. El desplazamiento <<
// descarta los bits que salen por la izquierda.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 Desplazamientos.java
// Ejecutar: java Desplazamientos

public class Desplazamientos {
    public static void main(String[] args) {
        int n = -8;
        System.out.println(n >> 1);
        System.out.println(n >>> 1);
        System.out.println(3 << 2);
    }
}

// Salida esperada:
// -4
// 2147483644
// 12
