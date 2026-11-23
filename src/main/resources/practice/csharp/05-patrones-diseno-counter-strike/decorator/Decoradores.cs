// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — etiquetar los avisos de ronda.
// Patrón: Decorator.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public interface IAvisoEquipo { string Texto(); }
public sealed record AvisoBase(string Mensaje) : IAvisoEquipo { public string Texto() => Mensaje; }
public sealed class Urgente(IAvisoEquipo interno) : IAvisoEquipo
{
    // El envoltorio conserva el aviso anterior; no modifica su instancia.
    public string Texto() => "[URGENTE] " + interno.Texto();
}
public sealed class DeRonda(IAvisoEquipo interno, int ronda) : IAvisoEquipo
{
    public string Texto() => $"[RONDA {ronda}] " + interno.Texto();
}
public static class DemoDecorator
{
    public static void Ejecutar()
    {
        IAvisoEquipo mensaje = new DeRonda(new Urgente(new AvisoBase("Rehén virtual rescatado")), 12);
        Console.WriteLine(mensaje.Texto());
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- decorator
 * Salida esperada:
 *   [RONDA 12] [URGENTE] Rehén virtual rescatado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
