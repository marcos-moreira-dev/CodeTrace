// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — rescate de NPC, economía y marcador de ronda.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed class ControladorPartida(VistaConsola vista)
{
    public Partida Simular()
    {
        var sala = new Configurador().Mapa("cs_office").Rondas(3).Rescate(true).Construir();
        var astra = new Jugador("Astra", "CT");
        // El objeto Astra es compartido entre el roster, tienda y rescate.
        var partida = new Partida(sala.Mapa, [astra, new("Nilo", "CT")],
            [new("Rook", "T"), new("Vega", "T")],
            [new("Elena", "Archivo", 92, 38), new("Omar", "Sala de juntas", 86, 24)]);
        var recursos = new CarpetaMapa("maps",
            new CarpetaMapa("oficiales", new ArchivoMapa("cs_office")), new ArchivoMapa("de_dust2"));
        vista.Presentar(partida, sala.Rondas, new AdaptadorPing(new()).Mostrar(), recursos.Contar());
        var cadena = Cadena.Crear();
        var observable = new MarcadorObservable();
        observable.PuntuacionCambiada += vista.Marcador;
        var economia = new Economia(Economia.Eco);
        // Guion equivalente a Java con semilla 42: comparamos las mismas tres rondas.
        bool[] desenlaces = [true, false, true];
        int indiceRehen = 0;
        for (int numero = 1; numero <= sala.Rondas; numero++)
        {
            partida.IniciarRonda();
            Console.WriteLine($"--- RONDA {numero} ---");
            economia.Politica = numero % 2 != 0 ? Economia.Eco : Economia.Equipo;
            Console.WriteLine(economia.Plan(astra.Creditos));
            if (numero % 2 == 0 && astra.Comprar(new("MP9", "subfusil", 950)))
                Console.WriteLine(cadena.Atender(new("equipo", "MP9 registrado como objeto virtual")));
            if (desenlaces[numero - 1])
            {
                // Referencia a la ficha del NPC que ya vive en Partida.
                // Rescatar modifica esa instancia; la vista verá el estado actualizado.
                var rehen = partida.Rehenes[indiceRehen++ % partida.Rehenes.Count];
                rehen.Rescatar(astra);
                partida.Reloj = 60;
                Console.WriteLine(cadena.Atender(new("rehen", $"{rehen.Nombre} llegó a la zona de extracción")));
                vista.Ficha(rehen);
                partida.Registrar("CT", $"rescate de {rehen.Nombre}");
            }
            else
            {
                partida.Reloj = 0;
                Console.WriteLine("Tiempo agotado sin rescate");
                partida.Registrar("T", "objetivo sin completar");
            }
            astra.Recompensar();
            // Observer publica las puntuaciones nuevas a la vista suscrita.
            observable.Anotar("CT", partida.Marcador.Ct);
            observable.Anotar("T", partida.Marcador.T);
        }
        observable.PuntuacionCambiada -= vista.Marcador;
        vista.Finalizar(partida, new CreadorTexto().Publicar(partida.Marcador.Ct));
        return partida;
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
