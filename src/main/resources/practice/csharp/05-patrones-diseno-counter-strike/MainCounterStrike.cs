// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — rescate de NPC, economía y marcador de ronda.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public static class MainCounterStrike
{
    public static void Main(string[] args)
    {
        Console.OutputEncoding = System.Text.Encoding.UTF8;
        // Sin argumentos la partida avanza automáticamente; no hay ReadLine.
        if (args.Length == 0)
        {
            new FachadaCounterStrike().JugarPartida();
            return;
        }
        // Los delegados permiten estudiar un patrón aislado sin duplicar Main.
        var patrones = new Dictionary<string, Action>
        {
            ["abstract-factory"] = DemoAbstractFactory.Ejecutar,
            ["adapter"] = DemoAdapter.Ejecutar,
            ["builder"] = DemoBuilder.Ejecutar,
            ["chain-of-responsibility"] = Cadena.Ejecutar,
            ["command"] = DemoCommand.Ejecutar,
            ["composite"] = DemoComposite.Ejecutar,
            ["decorator"] = DemoDecorator.Ejecutar,
            ["factory"] = FabricaChat.Ejecutar,
            ["factory-method"] = DemoFactoryMethod.Ejecutar,
            ["observer"] = DemoObserver.Ejecutar,
            ["strategy"] = DemoStrategy.Ejecutar,
            ["strategy-puntuacion"] = DemoPuntuacion.Ejecutar,
            ["template-method"] = DemoTemplate.Ejecutar,
        };
        patrones[args[0]]();
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
