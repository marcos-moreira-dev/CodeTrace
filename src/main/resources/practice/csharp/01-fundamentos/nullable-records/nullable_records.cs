// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: dotnet new console -o "$env:TEMP\ct-csharp"; Copy-Item '.\01-fundamentos\nullable-records\nullable_records.cs' "$env:TEMP\ct-csharp\Program.cs" -Force; dotnet build "$env:TEMP\ct-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\ct-csharp"

using System;

/**
 * Cliente de records, referencias anulables y pattern matching.
 *
 * Un record agrupa datos con igualdad por valor. Anotar Producto?
 * exige considerar el caso null con nullable habilitado. Un switch
 * por patrones distingue los posibles estados sin conversiones.
 */
public record Producto(string Codigo, decimal Precio);
public static class Program
{
    static string Informar(Producto? p) => p switch
    {
        null => "Sin producto",
        { Precio: < 0m } => "Precio incorrecto",
        { Precio: 0m } => "Gratis",
        _ => p.Codigo + ": " + p.Precio
    };
    public static void Main()
    {
        Console.WriteLine(Informar(new Producto("A", 9.99m)));
        Console.WriteLine(Informar(null));
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
 *   Copy-Item .\01-fundamentos\nullable-records\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   A: 9.99
 *   Sin producto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
