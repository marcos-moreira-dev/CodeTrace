// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Linq;
namespace CodeTrace.Practica.LinkedList.Simple;

/// <summary>
/// Cliente Linked List SIMPLE: inicio de la familia de listas enlazadas.
/// Cada nodo conserva una referencia a su siguiente, a diferencia
/// de Array/ArrayList. Agregar en cabeza O(1), recorrer O(n).
/// Contrastar con lista doble y lista circular en carpetas hermanas.
/// </summary>
public static class MainListaSimple
{
    public static void Main()
    {
        var l=new ListaSimple<string>();
        foreach(var x in new[]{"A","B","C"})l.AgregarPrimero(x);
        if(!l.Valores().SequenceEqual(new[]{"C","B","A"})||l.Cantidad!=3)
            throw new Exception("Lista simple");
        Console.WriteLine(string.Join(", ",l.Valores()));
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
 *   Copy-Item .\02-estructuras-datos\linked-list\simple\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   C, B, A
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
