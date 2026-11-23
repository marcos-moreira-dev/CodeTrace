// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — ping = latencia de conexión del servidor.
// Patrón: Adapter.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed class TelemetriaLegada
{
    public int Microsegundos => 42000;
}
public interface IVistaPing { string Mostrar(); }
public sealed class AdaptadorPing(TelemetriaLegada origen) : IVistaPing
{
    // El HUD espera milisegundos; el servidor antiguo entrega microsegundos.
    public string Mostrar() => $"Ping: {origen.Microsegundos / 1000} ms";
}
public static class DemoAdapter
{
    public static void Ejecutar() => Console.WriteLine(new AdaptadorPing(new()).Mostrar());
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- adapter
 * Salida esperada:
 *   Ping: 42 ms
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
