// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.Adapter;

/// <summary>
/// Cliente Adapter. Ni la vista ni las reglas de negocio requieren
/// conocer Fahrenheit; todo se expresa mediante ISensorCelsius.
/// Es composición, no una nueva jerarquía por una conversión trivial.
/// El caso enseña el desacoplamiento cuando la API no se puede editar.
/// </summary>
public static class MainAdapter
{
    public static void Main()
    {
        ISensorCelsius sensor = new AdaptadorSensor(new SensorExterno());
        if (sensor.Leer() != 25) throw new Exception("Conversión");
        Console.WriteLine($"Temperatura: {sensor.Leer()} °C");
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
 *   Copy-Item .\04-patrones-diseno\adapter\sensores\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Temperatura: 25 °C
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
