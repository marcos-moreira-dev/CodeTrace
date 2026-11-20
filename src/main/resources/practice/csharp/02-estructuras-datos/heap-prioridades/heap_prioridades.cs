// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: dotnet new console -o "$env:TEMP\ct-csharp"; Copy-Item '.\02-estructuras-datos\heap-prioridades\heap_prioridades.cs' "$env:TEMP\ct-csharp\Program.cs" -Force; dotnet build "$env:TEMP\ct-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\ct-csharp"

using System;
using System.Collections.Generic;

/**
 * Cliente de PriorityQueue<TElement,TPriority> en .NET 8.
 *
 * El heap entrega el elemento con menor prioridad primero. Enqueue
 * y Dequeue son O(log n). No se garantiza estabilidad para empates,
 * por lo que, si fuera necesario, se añadiría un criterio secundario.
 */
public static class Program
{
    public static void Main()
    {
        var q = new PriorityQueue<string, int>();
        q.Enqueue("informe", 3);
        q.Enqueue("factura", 1);
        q.Enqueue("correo", 2);
        while (q.Count > 0) Console.WriteLine(q.Dequeue());
    }
}
