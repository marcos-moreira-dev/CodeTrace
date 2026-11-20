// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.Trie;

/// <summary>
/// Trie por chars UTF-16 y diccionarios. Operaciones O(L) promedio
/// sobre L caracteres. Un prefijo no implica que la palabra exista.
/// </summary>
public sealed class Trie
{
    private sealed class Nodo
    {
        public Dictionary<char,Nodo> Hijos=new();
        public bool Fin;
    }
    private readonly Nodo _raiz=new();
    public void Insertar(string texto)
    {
        var n=_raiz;
        foreach(char c in texto)
        {
            if(!n.Hijos.TryGetValue(c,out var sig))
            {
                sig=new Nodo();
                n.Hijos.Add(c,sig);
            }
            n=sig;
        }
        n.Fin=true;
    }
    private Nodo? BuscarNodo(string texto)
    {
        var n=_raiz;
        foreach(char c in texto)
        {
            if(!n.Hijos.TryGetValue(c,out var sig))return null;
            n=sig;
        }
        return n;
    }
    public bool Contiene(string palabra)=>BuscarNodo(palabra)?.Fin==true;
    public bool Prefijo(string texto)=>BuscarNodo(texto) is not null;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\02-estructuras-datos\trie\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: Trie.cs es un componente; la salida corresponde al cliente MainTrie.cs.
 * Salida esperada:
 *   Trie de prefijos correcto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
