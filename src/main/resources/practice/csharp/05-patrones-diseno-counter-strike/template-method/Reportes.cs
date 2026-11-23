// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — informe de ronda en texto o CSV.
// Patrón: Template Method.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public abstract class Reporte
{
    // Todas las variantes construyen el encabezado antes de los puntos CT.
    public string Generar(string mapa, int puntos) => Encabezado(mapa) + Detalle(puntos);
    protected abstract string Encabezado(string mapa);
    protected abstract string Detalle(int puntos);
}
public sealed class ReporteTexto : Reporte
{
    protected override string Encabezado(string mapa) => $"Partida {mapa}: ";
    protected override string Detalle(int puntos) => $"CT={puntos}";
}
public sealed class ReporteCsv : Reporte
{
    protected override string Encabezado(string mapa) => $"mapa,rondasCt;{mapa},";
    protected override string Detalle(int puntos) => puntos.ToString();
}
public static class DemoTemplate
{
    public static void Ejecutar()
    {
        Console.WriteLine("Texto: " + new ReporteTexto().Generar("de_dust2", 7));
        Console.WriteLine("CSV: " + new ReporteCsv().Generar("de_dust2", 7));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- template-method
 * Salida esperada:
 *   Texto: Partida de_dust2: CT=7
 *   CSV: mapa,rondasCt;de_dust2,7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
