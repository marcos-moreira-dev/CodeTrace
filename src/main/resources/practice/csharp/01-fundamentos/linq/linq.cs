// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar/Comprobar: dotnet new console -o $env:TEMP\ct-csharp; Copy-Item ARCHIVO.cs $env:TEMP\ct-csharp\Program.cs -Force; dotnet build $env:TEMP\ct-csharp
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
