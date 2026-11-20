// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.DSU;

/// <summary>
/// Cliente Union-Find: conectividad transitiva para algoritmos como
/// Kruskal. No se reconstruye un camino, sino un representante por
/// conjunto. Una segunda unión interna no reduce Componentes.
/// </summary>
public static class MainUnionFind
{
    public static void Main()
    {
        var uf=new UnionFind(5);
        if(!uf.Unir(0,1)||!uf.Unir(1,2)||uf.Unir(0,2)||
           !uf.Conectados(0,2)||uf.Conectados(0,4)||
           uf.Componentes!=3)throw new Exception("DSU");
        Console.WriteLine($"Componentes: {uf.Componentes}");
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
 *   Copy-Item .\02-estructuras-datos\union-find\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Componentes: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
