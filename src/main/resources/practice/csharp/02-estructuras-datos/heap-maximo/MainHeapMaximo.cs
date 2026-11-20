// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Linq;
namespace CodeTrace.Practica.HeapMaximo;

/// <summary>
/// Cliente heap máximo vs PriorityQueue estándar de .NET.
/// El heap no ordena todo su arreglo: solamente garantiza la
/// relación padre &gt;= hijos. Extraer máximo cuesta O(log n).
/// </summary>
public static class MainHeapMaximo
{
    public static void Main()
    {
        var heap=new HeapMaximo();
        foreach(int n in new[]{4,1,9,7,2})heap.Insertar(n);
        if(!Enumerable.Range(0,5).Select(_=>heap.Extraer())
                .SequenceEqual(new[]{9,7,4,2,1}))
            throw new Exception("Heap máximo");
        Console.WriteLine("Max heap correcto");
    }
}
