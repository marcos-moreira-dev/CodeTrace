// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: dotnet new console -o "$env:TEMP\ct-csharp"; Copy-Item '.\01-fundamentos\yield-iteradores\yield_iteradores.cs' "$env:TEMP\ct-csharp\Program.cs" -Force; dotnet build "$env:TEMP\ct-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\ct-csharp"

using System;
using System.Collections.Generic;
using System.Linq;

/**
 * Cliente de yield return e IEnumerable.
 *
 * El iterador genera datos cuando el consumidor los pide. Take(8)
 * termina una sucesión potencialmente infinita sin almacenar todos
 * sus resultados. El enumerador conserva estado entre MoveNext.
 */
public static class Program
{
    static IEnumerable<int> Fibonacci()
    {
        int a = 0, b = 1;
        while (true)
        {
            yield return a;
            (a, b) = (b, a + b);
        }
    }
    public static void Main() =>
        Console.WriteLine(string.Join(", ", Fibonacci().Take(8)));
}
