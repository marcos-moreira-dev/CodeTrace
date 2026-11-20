// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

namespace CodeTrace.Practica.Strategy;

/// <summary>Contrato Strategy: un algoritmo de precio intercambiable.</summary>
public interface ITarifa
{
    decimal Total(decimal subtotal);
}

/// <summary>Implementaciones que pueden variar sin reescribir el contexto.</summary>
public sealed class TarifaNormal : ITarifa
{
    public decimal Total(decimal subtotal) => subtotal;
}
public sealed class TarifaFidelidad : ITarifa
{
    public decimal Total(decimal subtotal) => subtotal * 0.90m;
}

/// <summary>Contexto: el dominio conserva la delegación a ITarifa.</summary>
public sealed class Facturador
{
    public decimal Cobrar(decimal subtotal, ITarifa tarifa)
    {
        if (subtotal < 0) throw new ArgumentOutOfRangeException(nameof(subtotal));
        return tarifa.Total(subtotal);
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
 *   Copy-Item .\04-patrones-diseno\strategy\tarifas\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: Tarifas.cs es un componente; la salida corresponde al cliente MainStrategy.cs.
 * Salida esperada:
 *   Normal=100; fidelidad=90
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
