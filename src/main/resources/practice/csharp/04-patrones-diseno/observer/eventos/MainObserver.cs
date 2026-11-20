// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.ObserverEventos;

/// <summary>
/// Cliente Observer basado en events de .NET, no en una lista Java.
/// El emisor conoce únicamente un evento y no las acciones de
/// observadores. += suscribe; -= desuscribe; esto importa para
/// el ciclo de vida de vistas o servicios.
/// Esta unidad contrasta con observer/ que usa interfaces explícitas.
/// </summary>
public static class MainObserver
{
    public static void Main()
    {
        var emisor = new EmisorPedidos();
        int notificaciones = 0;
        EventHandler<PedidoEventArgs> observador = (_, evento) =>
        {
            Console.WriteLine("Pedido " + evento.Codigo);
            notificaciones++;
        };
        emisor.Despachado += observador;
        emisor.Despachar("A-001");
        emisor.Despachado -= observador;
        emisor.Despachar("B-002");
        if (notificaciones != 1) throw new Exception("Desuscripción");
    }
}
