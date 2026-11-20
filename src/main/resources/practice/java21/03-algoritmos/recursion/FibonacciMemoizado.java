// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete (PowerShell): New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.fibonacci.FibonacciMemoizado

package codetrace.practica.fibonacci;

import java.util.Arrays;

/**
 * Cliente de memoización, recursión y subproblemas repetidos.
 *
 * <p>La definición F(n)=F(n-1)+F(n-2) se traduce directamente, pero
 * la recursión ingenua recalcula innumerables valores. Guardarlos
 * en un arreglo reduce el tiempo a O(n), usando O(n) memoria para
 * tabla y pila. Long desborda para valores suficientemente grandes;
 * se limita n a 92, el último representable en un long firmado.
 */
public final class FibonacciMemoizado {
    private FibonacciMemoizado() {}

    private static long calcular(int n, long[] cache) {
        if (n < 2) return n;
        if (cache[n] >= 0) return cache[n];
        return cache[n] = Math.addExact(calcular(n - 1, cache), calcular(n - 2, cache));
    }

    public static long fibonacci(int n) {
        if (n < 0 || n > 92) throw new IllegalArgumentException("0 <= n <= 92");
        long[] cache = new long[n + 1];
        Arrays.fill(cache, -1);
        return calcular(n, cache);
    }

    public static void main(String[] args) {
        System.out.println("F(40) = " + fibonacci(40));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.fibonacci.FibonacciMemoizado
 * Salida esperada:
 *   F(40) = 102334155
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
