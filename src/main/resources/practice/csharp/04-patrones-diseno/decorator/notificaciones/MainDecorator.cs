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
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\04-patrones-diseno\decorator\notificaciones\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   [INFO] Pedido confirmado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
