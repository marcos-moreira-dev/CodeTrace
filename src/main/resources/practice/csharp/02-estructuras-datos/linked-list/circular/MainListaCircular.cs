// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Linq;
namespace CodeTrace.Practica.LinkedList.Circular;

/// <summary>
/// Cliente lista circular: la cabeza y cola forman un anillo.
/// Un recorrido hasta encontrar null jamás terminaría: la cantidad
/// de nodos controla la iteración. Rotar referencia O(1).
/// </summary>
public static class MainListaCircular
{
    public static void Main()
    {
        var l=new ListaCircular<string>();
        if(l.Valores().Count!=0)throw new Exception("Vacía");
        foreach(var x in new[]{"Ana","Luis","Mia"})l.Agregar(x);
        l.Rotar();
        if(!l.Valores().SequenceEqual(new[]{"Luis","Mia","Ana"}))
            throw new Exception("Circular");
        Console.WriteLine(string.Join(", ",l.Valores()));
    }
}
