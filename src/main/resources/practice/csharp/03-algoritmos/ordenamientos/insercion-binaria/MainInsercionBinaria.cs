// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-sorting" --force; Remove-Item "$env:TEMP\ct-sorting\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-sorting"; dotnet build "$env:TEMP\ct-sorting"
// Ejecutar: dotnet run --project "$env:TEMP\ct-sorting"

using System;
using System.Linq;
namespace CodeTrace.Practica.Algoritmos.InsercionBinaria;
/// <summary>
/// Cliente: insercion binaria no es algoritmo O(n log n) TOTAL.
/// Sus comparaciones se reducen, pero el costo de mover
/// elementos por insercion sigue siendo cuadratico.
/// </summary>
public static class MainInsercionBinaria{
    public static void Main(){
        int[] a={5,1,3,3,2};
        InsercionBinaria.Ordenar(a);
        if(!a.SequenceEqual(new[]{1,2,3,3,5}))throw new Exception("Orden");
        InsercionBinaria.Ordenar(Array.Empty<int>());
        Console.WriteLine("Insercion binaria: ["+string.Join(", ",a)+"]");
    }
}
/*
 * Salida esperada:
 * Insercion binaria: [1, 2, 3, 3, 5]
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\03-algoritmos\ordenamientos\insercion-binaria\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
