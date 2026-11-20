// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.FactoryMethod;

/// <summary>
/// Cliente Factory Method: NO confundir con una simple función switch.
/// El creador abstracto define el método de fábrica, una subclase
/// concreta decide qué producto fabricar y el flujo Preparar se
/// comparte entre creadores. Contrato y producto pueden evolucionar.
/// </summary>
public static class MainFactoryMethod
{
    public static void Main()
    {
        Creador creador = new CreadorTexto();
        string salida = creador.Preparar("Factura 001");
        if (salida != "TXT: Factura 001") throw new Exception("Fábrica");
        Console.WriteLine(salida);
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
 *   Copy-Item .\04-patrones-diseno\factory-method\exportadores\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   TXT: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
