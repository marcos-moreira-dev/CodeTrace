// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

namespace CodeTrace.Practica.Adapter;

/// <summary>Contrato de dominio en Celsius.</summary>
public interface ISensorCelsius { double Leer(); }

/// <summary>Proveedor legado o tercero: solo ofrece Fahrenheit.</summary>
public sealed class SensorExterno
{
    public double LeerFahrenheit() => 77.0;
}

/// <summary>Adapter convierte unidad y forma de la API externa.</summary>
public sealed class AdaptadorSensor : ISensorCelsius
{
    private readonly SensorExterno _externo;
    public AdaptadorSensor(SensorExterno externo) => _externo = externo;
    public double Leer() => (_externo.LeerFahrenheit() - 32) * 5 / 9;
}
