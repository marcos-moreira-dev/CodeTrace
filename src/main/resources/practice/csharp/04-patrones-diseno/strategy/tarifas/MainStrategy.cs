// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.Strategy;

/// <summary>
/// Cliente Strategy. Facturador no tiene if por tipo de cliente y no
/// necesita modificaciones cuando aparecen reglas nuevas.
/// Separamos contrato ITarifa, implementaciones, contexto y cliente.
/// C# permite además implementar políticas con delegates/lambdas,
/// pero aquí se muestran objetos para comparar con Java y Kotlin.
/// </summary>
public static class MainStrategy
{
    public static void Main()
    {
        var facturador = new Facturador();
        var normal = facturador.Cobrar(100, new TarifaNormal());
        var fidelidad = facturador.Cobrar(100, new TarifaFidelidad());
        if (normal != 100 || fidelidad != 90) throw new Exception("Tarifa");
        Console.WriteLine($"Normal={normal}; fidelidad={fidelidad}");
    }
}
