// Tema:
// La división sucesiva por dos obtiene los dígitos desde el menos significativo. Se
// invierte el orden al construir la representación de un entero no negativo.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 ConversionManual.java
// Ejecutar: java ConversionManual

public class ConversionManual {
    public static void main(String[] args) {
        int n = 42;
        String binario = "";
        do {
            binario = (n % 2) + binario;
            n /= 2;
        } while (n != 0);
        System.out.println(binario);
    }
}

// Salida esperada:
// 101010
