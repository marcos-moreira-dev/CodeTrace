// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.GrafosPonderados;

/// <summary>
/// Cliente grafos ponderados: primero modelar datos, luego escoger
/// algoritmo. Las aristas dirigidas tienen pesos independientes
/// de sus inversas. Dijkstra y Bellman-Ford tienen precondiciones
/// distintas; el grafo no selecciona cuál es correcto.
/// </summary>
public static class MainGrafoPonderado
{
    public static void Main()
    {
        var g=new GrafoPonderado();
        g.Conectar("GYE","UIO",12);
        g.Conectar("GYE","CUE",18);
        g.Conectar("UIO","CUE",8);
        if(g.Vecinos("GYE").Count!=2||g.Vecinos("CUE").Count!=0)
            throw new Exception("Arcos");
        Console.WriteLine($"Arcos desde GYE: {g.Vecinos("GYE").Count}");
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
 *   Copy-Item .\02-estructuras-datos\grafos\ponderados\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Arcos desde GYE: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
