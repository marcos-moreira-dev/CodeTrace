// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: dotnet new console -o "$env:TEMP\ct-csharp"; Copy-Item '.\01-fundamentos\async-tasks\async_tasks.cs' "$env:TEMP\ct-csharp\Program.cs" -Force; dotnet build "$env:TEMP\ct-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\ct-csharp"

using System;
using System.Threading.Tasks;

/**
 * Cliente de Task y async/await sin recursos externos.
 *
 * Task representa una operación cuyo resultado se conoce luego.
 * await suspende la continuación sin bloquear el hilo de llamada.
 * WhenAll obtiene los resultados de múltiples operaciones.
 */
public static class Program
{
    static async Task<int> CalcularAsync(int x)
    {
        await Task.Yield();
        return x * 2;
    }
    public static async Task Main()
    {
        var resultados = await Task.WhenAll(CalcularAsync(3), CalcularAsync(5));
        Console.WriteLine(string.Join(", ", resultados));
    }
}
