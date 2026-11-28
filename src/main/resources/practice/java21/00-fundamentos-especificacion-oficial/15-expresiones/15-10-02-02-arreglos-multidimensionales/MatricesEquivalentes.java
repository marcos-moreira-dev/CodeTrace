// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.10.2; ejemplo 15.10.2-2
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
