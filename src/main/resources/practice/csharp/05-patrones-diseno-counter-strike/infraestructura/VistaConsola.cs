// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — rescate de NPC, economía y marcador de ronda.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed class VistaConsola
{
    private int ct;
    public void Presentar(Partida partida, int rondas, string ping, int recursos)
    {
        Console.WriteLine("=== COUNTER-STRIKE | PARTIDA DIDÁCTICA ===");
        Console.WriteLine($"Mapa: {partida.Mapa} | Rondas: {rondas} | {ping}");
        // LINQ recorre las fichas para mostrar los alias de cada equipo.
        string nombresCt = string.Join(", ", partida.Ct.Select(j => j.Alias));
        string nombresT = string.Join(", ", partida.T.Select(j => j.Alias));
        Console.WriteLine($"Equipos: CT ({nombresCt}) vs T ({nombresT})");
        Console.WriteLine($"Mapas registrados: {recursos}");
    }
    public void Ficha(Rehen rehen) =>
        Console.WriteLine($"Rehén {rehen.Nombre}: {rehen.Estado.ToString().ToUpperInvariant()} | escolta={rehen.Escolta} | calma={rehen.Calma} | integridad={rehen.Integridad}");
    public void Marcador(string equipo, int puntos)
    {
        // El evento CT llega antes que T; mostramos ambos cuando llega T.
        if (equipo == "CT") ct = puntos;
        else Console.WriteLine($"Marcador: CT {ct} - {puntos} T");
    }
    public void Finalizar(Partida partida, string reporte)
    {
        var m = partida.Marcador;
        string resultado = m.Ct > m.T ? "Gana CT" : m.T > m.Ct ? "Gana T" : "Empate";
        Console.WriteLine("=== FIN DE LA PARTIDA ===");
        Console.WriteLine($"Resultado: {resultado}");
        Console.WriteLine($"Créditos Astra: {partida.Ct[0].Creditos}");
        Console.WriteLine($"Reporte: {reporte}");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   Mapa: cs_office | Rondas: 3 | Ping: 42 ms
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 *   Mapas registrados: 2
 *   --- RONDA 1 ---
 *   Eco: guardar 1600 créditos
 *   Rescate: Elena llegó a la zona de extracción
 *   Rehén Elena: RESCATADO | escolta=Astra | calma=50 | integridad=92
 *   Marcador: CT 1 - 0 T
 *   --- RONDA 2 ---
 *   Equipo: asignar 1900 créditos virtuales
 *   Inventario: MP9 registrado como objeto virtual
 *   Tiempo agotado sin rescate
 *   Marcador: CT 1 - 1 T
 *   --- RONDA 3 ---
 *   Eco: guardar 1250 créditos
 *   Rescate: Omar llegó a la zona de extracción
 *   Rehén Omar: RESCATADO | escolta=Astra | calma=36 | integridad=86
 *   Marcador: CT 2 - 1 T
 *   === FIN DE LA PARTIDA ===
 *   Resultado: Gana CT
 *   Créditos Astra: 1550
 *   Reporte: CT=2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
