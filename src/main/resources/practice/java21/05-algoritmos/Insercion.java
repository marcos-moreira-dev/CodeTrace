public final class Insercion {
    public static void ordenar(int[] numeros) {
        for (int i = 1; i < numeros.length; i++) {
            int actual = numeros[i];
            int j = i - 1;
            while (j >= 0 && numeros[j] > actual) {
                numeros[j + 1] = numeros[j];
                j--;
            }
            numeros[j + 1] = actual;
        }
    }
}
