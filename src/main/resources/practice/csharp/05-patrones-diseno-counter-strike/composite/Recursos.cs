// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — árbol de mapas virtuales del videojuego.
// Patrón: Composite.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public interface IRecurso { int Contar(); }
public sealed record ArchivoMapa(string Nombre) : IRecurso { public int Contar() => 1; }
public sealed class CarpetaMapa(string nombre, params IRecurso[] hijos) : IRecurso
{
    public string Nombre { get; } = nombre;
    // LINQ suma tanto hojas como carpetas, sin preguntar su tipo concreto.
    public int Contar() => hijos.Sum(hijo => hijo.Contar());
}
public static class DemoComposite
{
    public static void Ejecutar()
    {
        var mapas = new CarpetaMapa("maps",
            new CarpetaMapa("oficiales", new ArchivoMapa("de_dust2"), new ArchivoMapa("de_inferno")),
            new ArchivoMapa("cs_office"));
        Console.WriteLine($"Mapas registrados: {mapas.Contar()}");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- composite
 * Salida esperada:
 *   Mapas registrados: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
