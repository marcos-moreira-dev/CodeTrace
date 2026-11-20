// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: dotnet new console -o "$env:TEMP\ct-csharp"; Copy-Item '.\03-algoritmos\mochila-01\mochila_01.cs' "$env:TEMP\ct-csharp\Program.cs" -Force; dotnet build "$env:TEMP\ct-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\ct-csharp"

using System;

/**
 * Cliente de mochila 0/1 con programación dinámica.
 *
 * El recorrido descendente de capacidad permite utilizar cada
 * artículo como máximo una vez. O(n*C) tiempo, O(C) memoria.
 * La complejidad es seudopolinómica en la capacidad C.
 */
public static class Program
{
    static int Resolver(int[] pesos, int[] valores, int capacidad)
    {
        if (capacidad < 0 || pesos.Length != valores.Length)
            throw new ArgumentException("Entradas");
        var dp = new int[capacidad + 1];
        for (int i = 0; i < pesos.Length; i++)
        {
            if (pesos[i] <= 0 || valores[i] < 0)
                throw new ArgumentException("Artículo");
            for (int c = capacidad; c >= pesos[i]; c--)
                dp[c] = Math.Max(dp[c], dp[c - pesos[i]] + valores[i]);
        }
        return dp[capacidad];
    }
    public static void Main() =>
        Console.WriteLine(Resolver(new[] {2, 3, 4, 5}, new[] {3, 4, 5, 8}, 8));
}
