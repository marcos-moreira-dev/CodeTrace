// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.Grafo;

/// <summary>
/// Grafo dirigido y ponderado representado con listas de adyacencia.
/// Cada arco A->B se registra una sola vez; no se presume B->A.
/// Memoria O(V+E), adecuada para grafos dispersos.
/// </summary>
public sealed class Grafo
{
    private readonly Dictionary<string, Dictionary<string, double>> _ady = new();
    public void Vertice(string id)
    {
        if (string.IsNullOrWhiteSpace(id)) throw new ArgumentException("Vértice");
        _ady.TryAdd(id, new());
    }
    public void Conectar(string a, string b, double peso)
    {
        if (a == b || !double.IsFinite(peso)) throw new ArgumentException("Arista");
        Vertice(a); Vertice(b);
        _ady[a][b] = peso;
    }
    public IReadOnlyDictionary<string, double> Vecinos(string id) =>
        new Dictionary<string, double>(_ady[id]);
}
