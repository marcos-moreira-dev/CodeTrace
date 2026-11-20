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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\04-patrones-diseno\decorator\notificaciones\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: Notificadores.cs es un componente; la salida corresponde al cliente MainDecorator.cs.
 * Salida esperada:
 *   [INFO] Pedido confirmado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
