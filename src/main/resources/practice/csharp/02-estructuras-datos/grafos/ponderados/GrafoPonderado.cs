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
