// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
namespace CodeTrace.Practica.Patrones.Composite;

/// <summary>
/// Cliente Composite: tratar uniformemente Archivo y Carpeta por
/// IElemento, y anidarlos en un árbol. La suma recorre O(n) nodos.
/// No es un explorador real del sistema de archivos.
/// </summary>
public static class MainComposite
{
    public static void Main()
    {
        var raiz=new Carpeta("proyecto");
        var src=new Carpeta("src");
        src.Agregar(new Archivo("Main.cs",120));
        src.Agregar(new Archivo("App.cs",80));
        raiz.Agregar(src);raiz.Agregar(new Archivo("README.md",20));
        IElemento e=raiz;
        if(e.Bytes()!=220)throw new Exception("Composite");
        Console.WriteLine($"Bytes: {e.Bytes()}");
    }
}

/*
 * Salida esperada de consola:
 *   Bytes: 220
 * IElemento permite consultar el tamaño de la carpeta completa.
 */
