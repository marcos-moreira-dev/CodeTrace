// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — el HUD recibe cambios de puntuación mediante eventos .NET.
// Patrón: Observer.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed class MarcadorObservable
{
    public event Action<string, int>? PuntuacionCambiada;
    public void Anotar(string equipo, int puntos)
    {
        // Invoke publica el resultado a todos los espectadores suscritos.
        PuntuacionCambiada?.Invoke(equipo, puntos);
    }
}
public static class DemoObserver
{
    public static void Ejecutar()
    {
        var marcador = new MarcadorObservable();
        Action<string, int> hud = (e, p) => Console.WriteLine($"HUD: {e}={p}");
        marcador.PuntuacionCambiada += hud;
        marcador.Anotar("CT", 2);
        marcador.Anotar("T", 1);
        // Al terminar la partida, la vista deja de escuchar al marcador.
        marcador.PuntuacionCambiada -= hud;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- observer
 * Salida esperada:
 *   HUD: CT=2
 *   HUD: T=1
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
