// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar/Comprobar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-biblioteca" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: Consulte MainMonedas

package codetrace.practica.monedas;

import java.util.Arrays;

/**
 * Dos técnicas para cambio de monedas, con denominaciones positivas.
 * La implementación voraz elige siempre la mayor moneda disponible,
 * mientras que la dinámica calcula el número mínimo de monedas.
 */
public final class Monedas {
    private Monedas() {}

    /** Devuelve una cantidad factible, no necesariamente mínima; -1 si no hay solución. */
    public static int voraz(int[] valores, int monto) {
        validar(valores, monto);
        int resto = monto, usados = 0;
        int[] copia = valores.clone();
        Arrays.sort(copia);
        for (int i = copia.length - 1; i >= 0; i--) {
            usados += resto / copia[i];
            resto %= copia[i];
        }
        return resto == 0 ? usados : -1;
    }

    /**
     * Calcula un mínimo real: O(monto * cantidadDenominaciones) tiempo,
     * O(monto) memoria. Devolvemos -1 para montos imposibles.
     */
    public static int dinamica(int[] valores, int monto) {
        validar(valores, monto);
        int infinito = monto + 1;
        int[] dp = new int[monto + 1];
        Arrays.fill(dp, infinito);
        dp[0] = 0;
        for (int m = 1; m <= monto; m++) {
            for (int valor : valores) {
                if (valor <= m && dp[m - valor] != infinito) {
                    dp[m] = Math.min(dp[m], dp[m - valor] + 1);
                }
            }
        }
        return dp[monto] == infinito ? -1 : dp[monto];
    }

    private static void validar(int[] valores, int monto) {
        if (valores == null || valores.length == 0 || monto < 0)
            throw new IllegalArgumentException("Entrada inválida");
        for (int valor : valores)
            if (valor <= 0) throw new IllegalArgumentException("Moneda no positiva");
    }
}
