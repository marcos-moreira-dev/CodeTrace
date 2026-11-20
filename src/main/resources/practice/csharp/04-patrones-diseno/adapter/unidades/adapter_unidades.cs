// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: dotnet new console -o "$env:TEMP\ct-csharp"; Copy-Item '.\04-patrones-diseno\adapter\unidades\adapter_unidades.cs' "$env:TEMP\ct-csharp\Program.cs" -Force; dotnet build "$env:TEMP\ct-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\ct-csharp"

using System;

/**
 * Cliente Adapter: transformar una interfaz externa a la del dominio.
 *
 * La aplicación solo utiliza ISensorCelsius. Un proveedor legado
 * sigue entregando Fahrenheit y el adaptador traduce sus lecturas.
 * Así el contrato propio no depende de la implementación externa.
 */
public interface ISensorCelsius { double Leer(); }
public sealed class Legado
{
    public double Fahrenheit() => 77;
}
public sealed class Adaptador : ISensorCelsius
{
    private readonly Legado _externo;
    public Adaptador(Legado externo) => _externo = externo;
    public double Leer() => (_externo.Fahrenheit() - 32) * 5 / 9;
}
public static class Program
{
    public static void Main()
    {
        ISensorCelsius sensor = new Adaptador(new Legado());
        Console.WriteLine("Celsius: " + sensor.Leer());
    }
}
