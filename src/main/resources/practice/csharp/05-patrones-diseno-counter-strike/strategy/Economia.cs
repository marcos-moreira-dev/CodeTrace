// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — eco = ahorro de créditos ficticios.
// Patrón: Strategy.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed class Economia(Func<int, string> politica)
{
    public Func<int, string> Politica { get; set; } = politica;
    public string Plan(int creditos) => Politica(creditos);
    public static string Eco(int creditos) => $"Eco: guardar {creditos} créditos";
    public static string Equipo(int creditos) => $"Equipo: asignar {creditos} créditos virtuales";
}
public static class DemoStrategy
{
    public static void Ejecutar()
    {
        var economia = new Economia(Economia.Eco);
        Console.WriteLine(economia.Plan(1500));
        // Cambiamos el delegado, no el saldo del jugador.
        economia.Politica = Economia.Equipo;
        Console.WriteLine(economia.Plan(1500));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- strategy
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
