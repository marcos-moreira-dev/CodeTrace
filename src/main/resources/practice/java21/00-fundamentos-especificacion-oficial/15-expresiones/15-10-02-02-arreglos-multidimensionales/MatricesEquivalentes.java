// Tema:
// Un arreglo multidimensional es un arreglo de referencias a otros arreglos. Puede crearse de
// una vez o reservando sus subarreglos por separado.
// Compilar: javac --release 21 -encoding UTF-8 -d . MatricesEquivalentes.java
// Ejecutar: java MatricesEquivalentes
// Comandos desde la carpeta de la unidad.

public class MatricesEquivalentes {
    public static void main(String[] args) {
        float[][] primera = new float[2][3];
        float[][] segunda = new float[2][];
        for (int i = 0; i < segunda.length; i++) {
            segunda[i] = new float[3];
        }
        System.out.println(java.util.Arrays.deepEquals(primera, segunda));
    }
}

// Salida esperada:
// true
