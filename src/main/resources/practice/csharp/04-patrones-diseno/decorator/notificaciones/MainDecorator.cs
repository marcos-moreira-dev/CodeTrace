// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

namespace CodeTrace.Practica.Decorator;

/// <summary>
/// Cliente Decorator: varias funcionalidades se componen por capas.
/// El objeto base nunca cambia; se envuelve usando la misma interfaz.
/// Evita heredar todas las combinaciones posibles de comportamiento.
/// </summary>
public static class MainDecorator
{
    public static void Main()
    {
        INotificador notificador = new ConPrefijo(new NotificadorConsola(), "[INFO] ");
        notificador.Enviar("Pedido confirmado");
    }
}

/*
 * Salida esperada de consola:
 *   Sin salida de texto; las pruebas del cliente se cumplen.
 * Verificacion: termina sin excepciones inesperadas.
 */
