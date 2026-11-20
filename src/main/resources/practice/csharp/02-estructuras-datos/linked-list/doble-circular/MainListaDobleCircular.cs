// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Linq;
namespace CodeTrace.Practica.LinkedList.DobleCircular;

/// <summary>
/// Cliente lista doble circular: anillo bidireccional con prev/next.
/// El invariante se comprueba mediante recorridos limitados por
/// cantidad. No se recorre hasta null porque no existe terminador.
/// </summary>
public static class MainListaDobleCircular
{
    public static void Main()
    {
        var l=new ListaDobleCircular<int>();
        foreach(int x in new[]{2,3,5})l.Agregar(x);
        if(!l.Valores().SequenceEqual(new[]{2,3,5})||
           !l.Valores(true).SequenceEqual(new[]{5,3,2}))
            throw new Exception("Enlaces");
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
 *   Copy-Item .\02-estructuras-datos\linked-list\doble-circular\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   2, 3, 5
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
