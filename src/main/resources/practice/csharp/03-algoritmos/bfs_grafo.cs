// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: dotnet new console -o "$env:TEMP\ct-csharp"; Copy-Item '.\03-algoritmos\bfs_grafo.cs' "$env:TEMP\ct-csharp\Program.cs" -Force; dotnet build "$env:TEMP\ct-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\ct-csharp"

using System;
using System.Collections.Generic;

/**
 * Cliente de BFS con Dictionary, Queue y HashSet.
 *
 * Se marca cada vértice al entrar a la cola FIFO. El recorrido
 * descubre primero los nodos cercanos en número de aristas.
 * Sobre listas de adyacencia: O(V+E) tiempo y O(V) memoria.
 */
public static class Program
{
    static List<string> Bfs(Dictionary<string, string[]> g, string origen)
    {
        var salida = new List<string>();
        var vistos = new HashSet<string> { origen };
        var cola = new Queue<string>();
        cola.Enqueue(origen);
        while (cola.Count > 0)
        {
            var actual = cola.Dequeue();
            salida.Add(actual);
            foreach (var vecino in g[actual])
                if (vistos.Add(vecino)) cola.Enqueue(vecino);
        }
        return salida;
    }
    public static void Main()
    {
        var g = new Dictionary<string, string[]> {
            ["A"] = new[] {"B", "C"}, ["B"] = new[] {"D"},
            ["C"] = new[] {"E"}, ["D"] = Array.Empty<string>(),
            ["E"] = Array.Empty<string>()
        };
        Console.WriteLine(string.Join(", ", Bfs(g, "A")));
    }
}
