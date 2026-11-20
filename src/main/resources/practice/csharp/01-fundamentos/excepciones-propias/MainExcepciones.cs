// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.Excepciones;

/// <summary>
/// Cliente de excepciones personalizadas en C#.
/// Excepción de dominio (StockInsuficienteException), errores de
/// argumento (ArgumentOutOfRangeException), contrato e invariante.
/// La operación fallida no debe cambiar Disponible.
/// C# admite filtros catch when y finally en escenarios mayores.
/// </summary>
public static class MainExcepciones
{
    public static void Main()
    {
        var inventario = new Inventario(5);
        inventario.Despachar(2);
        try
        {
            inventario.Despachar(8);
            throw new Exception("Se aceptó pedido inválido");
        }
        catch (StockInsuficienteException ex) when (ex.Disponible == 3)
        {
            Console.WriteLine("Rechazo: " + ex.Message);
        }
        if (inventario.Disponible != 3) throw new Exception("Invariante");
    }
}
