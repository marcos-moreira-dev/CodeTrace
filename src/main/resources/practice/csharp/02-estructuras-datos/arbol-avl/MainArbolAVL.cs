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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\02-estructuras-datos\arbol-avl\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Altura AVL: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
