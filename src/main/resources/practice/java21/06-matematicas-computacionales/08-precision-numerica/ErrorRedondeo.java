// Tema:
// Muchas fracciones decimales no son representables exactamente en binario.
// Para comparar resultados aproximados se puede usar una tolerancia adecuada.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 ErrorRedondeo.java
// Ejecutar: java ErrorRedondeo

public class ErrorRedondeo {
    public static void main(String[] args) {
        double suma = 0.1 + 0.2;
        System.out.println(suma);
        System.out.println(suma == 0.3);
        System.out.println(Math.abs(suma - 0.3) < 1e-12);
    }
}

// Salida esperada:
// 0.30000000000000004
// false
// true
