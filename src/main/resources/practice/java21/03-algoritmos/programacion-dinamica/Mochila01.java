// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.mochila.Mochila01

package codetrace.practica.mochila;

/**
 * Cliente de la mochila 0/1 con capacidad entera no negativa.
 *
 * <p>Cada artículo puede elegirse cero o una vez. El estado dp[c]
 * representa el beneficio máximo para capacidad c usando los artículos
 * procesados. Iterar capacidad en orden DESCENDENTE evita utilizar
 * varias veces un mismo artículo: es la diferencia esencial frente
 * a la variante de mochila ilimitada.
 *
 * <p>Complejidad: O(n*C) tiempo, O(C) memoria. Esta complejidad es
 * seudopolinómica: C representa la capacidad numérica, no sus bits.
 */
public final class Mochila01 {
    private Mochila01() {}

    public static int mejorValor(int[] pesos, int[] valores, int capacidad) {
        if (pesos == null || valores == null || pesos.length != valores.length
                || capacidad < 0) throw new IllegalArgumentException("Entradas");
        int[] dp = new int[capacidad + 1];
        for (int i = 0; i < pesos.length; i++) {
            if (pesos[i] <= 0 || valores[i] < 0)
                throw new IllegalArgumentException("Peso/beneficio");
            for (int c = capacidad; c >= pesos[i]; c--) {
                dp[c] = Math.max(dp[c], dp[c - pesos[i]] + valores[i]);
            }
        }
        return dp[capacidad];
    }

    public static void main(String[] args) {
        int[] pesos = {2, 3, 4, 5};
        int[] valores = {3, 4, 5, 8};
        System.out.println("Óptimo: " + mejorValor(pesos, valores, 8));
    }
}
