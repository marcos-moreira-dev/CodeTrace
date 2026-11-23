// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — registrar y deshacer un punto CT.
// Patrón: Command.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed class Marcador
{
    public int Ct { get; private set; }
    public int T { get; private set; }
    public void SumarCt() => Ct++;
    public void SumarT() => T++;
    public void RestarCt() { if (Ct == 0) throw new InvalidOperationException(); Ct--; }
}
public interface IComando { void Ejecutar(); void Deshacer(); }
public sealed class SumarCt(Marcador receptor) : IComando
{
    private bool ejecutado;
    public void Ejecutar()
    {
        if (ejecutado) throw new InvalidOperationException();
        // El receptor es el marcador real de la partida, no una copia.
        receptor.SumarCt(); ejecutado = true;
    }
    public void Deshacer()
    {
        if (!ejecutado) throw new InvalidOperationException();
        receptor.RestarCt(); ejecutado = false;
    }
}
public static class DemoCommand
{
    public static void Ejecutar()
    {
        var marcador = new Marcador();
        IComando comando = new SumarCt(marcador);
        comando.Ejecutar();
        Console.WriteLine($"Tras ejecutar: CT={marcador.Ct}");
        comando.Deshacer();
        Console.WriteLine($"Tras deshacer: CT={marcador.Ct}");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- command
 * Salida esperada:
 *   Tras ejecutar: CT=1
 *   Tras deshacer: CT=0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
