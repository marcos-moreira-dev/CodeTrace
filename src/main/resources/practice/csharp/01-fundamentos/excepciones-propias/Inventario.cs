// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.Excepciones;

/// <summary>
/// Una excepción propia puede transportar un código de dominio;
/// C# no exige capturar excepciones de manera estática como Java.
/// </summary>
public sealed class StockInsuficienteException : Exception
{
    public int Disponible { get; }
    public StockInsuficienteException(int solicitado, int disponible) :
        base($"Pedido {solicitado}, disponible {disponible}")
        => Disponible = disponible;
}

/// <summary>Modelo de inventario: nunca deja stock negativo.</summary>
public sealed class Inventario
{
    public int Disponible { get; private set; }
    public Inventario(int unidades)
    {
        if (unidades < 0) throw new ArgumentOutOfRangeException(nameof(unidades));
        Disponible = unidades;
    }
    public void Despachar(int cantidad)
    {
        if (cantidad <= 0) throw new ArgumentOutOfRangeException(nameof(cantidad));
        if (cantidad > Disponible)
            throw new StockInsuficienteException(cantidad, Disponible);
        Disponible -= cantidad;
    }
}
