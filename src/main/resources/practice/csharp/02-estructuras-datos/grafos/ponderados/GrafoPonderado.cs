// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.GrafosPonderados;

/// <summary>Arista dirigida con costo; record inmutable del dominio.</summary>
public record Arista(string Destino,double Peso);

/// <summary>
/// Grafo dirigido de listas de adyacencia; memoria O(V+E).
/// Una conexión A->B no implica B->A; Dijkstra requiere pesos
/// no negativos, pero este modelo no impone ese algoritmo.
/// </summary>
public sealed class GrafoPonderado
{
    private readonly Dictionary<string,List<Arista>> _ady=new();
    public void Conectar(string origen,string destino,double peso)
    {
        if(!double.IsFinite(peso))throw new ArgumentException("Peso");
        if(!_ady.ContainsKey(origen))_ady[origen]=new();
        if(!_ady.ContainsKey(destino))_ady[destino]=new();
        _ady[origen].Add(new Arista(destino,peso));
    }
    public IReadOnlyList<Arista> Vecinos(string origen)=>_ady[origen].AsReadOnly();
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
 * Nota: GrafoPonderado.cs es un componente; la salida corresponde al cliente MainGrafoPonderado.cs.
 * Salida esperada:
 *   Arcos desde GYE: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
