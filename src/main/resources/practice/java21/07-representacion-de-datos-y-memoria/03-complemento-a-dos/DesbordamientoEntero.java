// Tema:
// La suma de enteros int conserva 32 bits; al superar el máximo se obtiene el mínimo.
// Math.addExact permite detectar el desbordamiento.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 DesbordamientoEntero.java
// Ejecutar: java DesbordamientoEntero

public class DesbordamientoEntero {
    public static void main(String[] args) {
        int maximo = Integer.MAX_VALUE;
        System.out.println(maximo + 1);
        try {
            Math.addExact(maximo, 1);
        } catch (ArithmeticException e) {
            System.out.println("Desbordamiento detectado");
        }
    }
}

// Salida esperada:
// -2147483648
// Desbordamiento detectado
