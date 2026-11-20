// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar/Comprobar: dotnet new console -o $env:TEMP\ct-csharp; Copy-Item 01-fundamentos/linq/linq.cs $env:TEMP\ct-csharp\Program.cs -Force; dotnet build $env:TEMP\ct-csharp
// Ejecutar: dotnet run --project $env:TEMP\ct-csharp

using System;
using System.Linq;
/**
 * Cliente de LINQ: filtrar, proyectar y evaluar.
 * Where selecciona productos y Select toma sus nombres. La consulta
 * IEnumerable es diferida hasta su enumeración, a diferencia de
 * materializar una colección con ToList. El filtro cuesta O(n).
 */
public record Producto(string Nombre, decimal Precio);
public static class Program
{
    public static void Main()
    {
        var datos = new[] {
            new Producto("Cable", 1.25m),
            new Producto("Sensor", 7.50m),
            new Producto("Placa", 12m)
        };
        var baratos = datos.Where(p => p.Precio < 10m).Select(p => p.Nombre);
        Console.WriteLine(string.Join(", ", baratos));
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
 *   Copy-Item .\01-fundamentos\linq\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Cable, Sensor
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
