// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
namespace CodeTrace.Practica.Hash;

/// <summary>
/// Cliente hash: colisiones y actualización de una clave.
/// La capacidad pequeña garantiza colisiones, sin confundir
/// HashCode con identidad. El API Try/Get evita usar un valor
/// centinela para expresar ausencia.
/// </summary>
public static class MainTablaHash
{
    public static void Main()
    {
        var tabla = new TablaHash<int>(2);
        tabla.Poner("A", 1); tabla.Poner("B", 2); tabla.Poner("A", 3);
        if (!tabla.Buscar("A", out int a) || a != 3 ||
            !tabla.Buscar("B", out int b) || b != 2 ||
            tabla.Buscar("Z", out _)) throw new Exception("Hash");
        Console.WriteLine($"A={a}, B={b}");
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
 *   Copy-Item .\02-estructuras-datos\tabla-hash\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   A=3, B=2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
