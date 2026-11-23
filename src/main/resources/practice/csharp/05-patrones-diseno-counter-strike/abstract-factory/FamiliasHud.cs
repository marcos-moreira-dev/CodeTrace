// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — HUD claro y oscuro para mostrar marcador e inventario.
// Patrón: Abstract Factory.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public interface IEtiquetaMarcador { string Mostrar(int puntos); }
public interface IEtiquetaInventario { string Mostrar(string item); }
public interface IFamiliaHud
{
    IEtiquetaMarcador Marcador();
    IEtiquetaInventario Inventario();
}
public sealed record EtiquetaMarcador(string Tema) : IEtiquetaMarcador
{
    public string Mostrar(int puntos) => $"[{Tema}] CT={puntos}";
}
public sealed record EtiquetaInventario(string Tema) : IEtiquetaInventario
{
    public string Mostrar(string item) => $"[{Tema}] equipo={item}";
}
public sealed class FamiliaHud(string tema) : IFamiliaHud
{
    // Los dos productos comparten el tema; el cliente no mezcla colores.
    public IEtiquetaMarcador Marcador() => new EtiquetaMarcador(tema);
    public IEtiquetaInventario Inventario() => new EtiquetaInventario(tema);
}
public static class DemoAbstractFactory
{
    public static void Ejecutar()
    {
        foreach (var (nombre, tema) in new[] { ("Claro", "CLARO"), ("Oscuro", "OSCURO") })
        {
            IFamiliaHud familia = new FamiliaHud(tema);
            Console.WriteLine($"{nombre}: {familia.Marcador().Mostrar(5)} | {familia.Inventario().Mostrar("MP9")}");
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- abstract-factory
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
