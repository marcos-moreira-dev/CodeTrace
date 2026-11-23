// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — publicar resultados de rondas.
// Patrón: Factory Method.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public interface IExportador { string Exportar(int puntos); }
public sealed class ExportadorTexto : IExportador { public string Exportar(int p) => $"CT={p}"; }
public sealed class ExportadorCsv : IExportador { public string Exportar(int p) => $"equipo,rondas;CT,{p}"; }
public abstract class CreadorReporte
{
    protected abstract IExportador Crear();
    // El flujo común delega al método fábrica la creación del exportador.
    public string Publicar(int puntos) => Crear().Exportar(puntos);
}
public sealed class CreadorTexto : CreadorReporte { protected override IExportador Crear() => new ExportadorTexto(); }
public sealed class CreadorCsv : CreadorReporte { protected override IExportador Crear() => new ExportadorCsv(); }
public static class DemoFactoryMethod
{
    public static void Ejecutar()
    {
        Console.WriteLine("Texto: " + new CreadorTexto().Publicar(7));
        Console.WriteLine("CSV: " + new CreadorCsv().Publicar(7));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- factory-method
 * Salida esperada:
 *   Texto: CT=7
 *   CSV: equipo,rondas;CT,7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
