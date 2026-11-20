// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.ObserverEventos;

/// <summary>Datos de una notificación tipada, independientes de la UI.</summary>
public sealed class PedidoEventArgs : EventArgs
{
    public string Codigo { get; }
    public PedidoEventArgs(string codigo) => Codigo = codigo;
}

/// <summary>
/// Observable idiomático de C#: event es más restrictivo que delegate.
/// Solo el emisor puede invocar el evento desde fuera de su clase.
/// Un suscriptor se desregistra con -= para evitar retener referencias.
/// </summary>
public sealed class EmisorPedidos
{
    public event EventHandler<PedidoEventArgs>? Despachado;
    public void Despachar(string codigo)
    {
        if (string.IsNullOrWhiteSpace(codigo)) throw new ArgumentException("Código");
        Despachado?.Invoke(this, new PedidoEventArgs(codigo));
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
 *   Copy-Item .\04-patrones-diseno\observer\eventos\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: EmisorPedidos.cs es un componente; la salida corresponde al cliente MainObserver.cs.
 * Salida esperada:
 *   Pedido A-001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
