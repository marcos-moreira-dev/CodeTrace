// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
using System.Linq;
namespace CodeTrace.Practica.BST;

/// <summary>
/// Cliente BST con recorrido inorden. Insertar elementos en un árbol
/// ordinario no garantiza balance; elegir un árbol en vez de un arreglo
/// tiene costos y ventajas que dependen de los accesos predominantes.
/// </summary>
public static class MainArbolBST
{
    public static void Main()
    {
        var bst = new ArbolBST<int>();
        foreach (int n in new[] { 8, 3, 10, 1, 6, 4, 7 }) bst.Insertar(n);
        int[] esperado = { 1, 3, 4, 6, 7, 8, 10 };
        if (!bst.Inorden().SequenceEqual(esperado)) throw new Exception("BST");
        Console.WriteLine(string.Join(", ", bst.Inorden()));
    }
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
