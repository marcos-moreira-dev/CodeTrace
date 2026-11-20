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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\01-fundamentos\async-tasks\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   6, 10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
