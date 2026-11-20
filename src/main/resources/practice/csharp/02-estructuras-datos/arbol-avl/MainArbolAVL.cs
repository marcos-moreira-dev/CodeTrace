// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Linq;
namespace CodeTrace.Practica.AVL;

/// <summary>
/// Cliente AVL: la entrada 1..15 es adversa para un BST simple,
/// pero un AVL usa rotaciones y conserva O(log n) en altura.
/// Se verifica orden inorden y el factor de balance recursivo.
/// </summary>
public static class MainArbolAVL
{
    public static void Main()
    {
        var arbol=new ArbolAVL();
        for(int i=1;i<=15;i++)arbol.Agregar(i);
        if(!arbol.Inorden().SequenceEqual(Enumerable.Range(1,15))||
           arbol.Validar()>5)throw new Exception("AVL");
        Console.WriteLine($"Altura AVL: {arbol.Validar()}");
    }
}
