// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.Decorator;

/// <summary>Contrato común entre componente básico y decoradores.</summary>
public interface INotificador { void Enviar(string mensaje); }

public sealed class NotificadorConsola : INotificador
{
    public void Enviar(string mensaje) => Console.WriteLine(mensaje);
}

/// <summary>Decorator: un INotificador contiene y delega a otro.</summary>
public sealed class ConPrefijo : INotificador
{
    private readonly INotificador _interno;
    private readonly string _prefijo;
    public ConPrefijo(INotificador interno, string prefijo)
    {
        _interno = interno;
        _prefijo = prefijo;
    }
    public void Enviar(string mensaje) => _interno.Enviar(_prefijo + mensaje);
}
