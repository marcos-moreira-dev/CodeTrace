// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.Deque;

/// <summary>
/// Cliente Deque: una secuencia con acceso por los dos extremos.
/// LinkedList&lt;T&gt; es idiomática para operaciones O(1) por nodo
/// en .NET; un arreglo circular también sería una implementación.
/// </summary>
public static class MainDeque
{
    public static void Main()
    {
        var d=new DequeCurso<string>();
        d.AgregarPrimero("B");d.AgregarPrimero("A");d.AgregarUltimo("C");
        if(d.QuitarPrimero()!="A"||d.QuitarUltimo()!="C"||
           d.QuitarUltimo()!="B")throw new Exception("Deque");
        Console.WriteLine("Deque comprobado");
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
 *   Copy-Item .\02-estructuras-datos\deque\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Deque comprobado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
