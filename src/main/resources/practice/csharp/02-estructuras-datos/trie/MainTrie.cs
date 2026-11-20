// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.Trie;

/// <summary>
/// Cliente Trie: diferenciar prefijo del final de palabra.
/// casa y casco comparten la ruta cas, pero cas no tiene marca
/// de palabra completa. No es lo mismo que buscar en un BST.
/// </summary>
public static class MainTrie
{
    public static void Main()
    {
        var t=new Trie();
        t.Insertar("casa");t.Insertar("casco");
        if(!t.Prefijo("cas")||t.Contiene("cas")||
           !t.Contiene("casa")||t.Contiene("cama"))throw new Exception("Trie");
        Console.WriteLine("Trie de prefijos correcto");
    }
}

/*
 * Salida esperada de consola:
 *   Trie de prefijos correcto
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
