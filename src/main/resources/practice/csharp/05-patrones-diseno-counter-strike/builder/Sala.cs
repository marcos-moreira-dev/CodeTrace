// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — configuración del mapa virtual cs_office y rondas.
// Patrón: Builder.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed record Sala(string Mapa, int Rondas, bool Rehenes);
public sealed class Configurador
{
    private string mapa = "de_dust2";
    private int rondas = 6;
    private bool rescate;
    public Configurador Mapa(string valor) { mapa = valor; return this; }
    public Configurador Rondas(int valor) { rondas = valor; return this; }
    public Configurador Rescate(bool valor) { rescate = valor; return this; }
    public Sala Construir()
    {
        // Solo al finalizar la configuración se crea el registro inmutable.
        if (string.IsNullOrWhiteSpace(mapa) || rondas <= 0)
            throw new ArgumentException("Sala inválida");
        return new Sala(mapa, rondas, rescate);
    }
}
public static class DemoBuilder
{
    public static void Ejecutar()
    {
        var s = new Configurador().Mapa("cs_office").Rondas(12).Rescate(true).Construir();
        Console.WriteLine($"Sala: {s.Mapa}, rondas={s.Rondas}, rehenes={s.Rehenes.ToString().ToLowerInvariant()}");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- builder
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
