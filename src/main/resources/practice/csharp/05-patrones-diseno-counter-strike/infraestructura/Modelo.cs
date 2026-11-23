// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — rescate de NPC, economía y marcador de ronda.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public enum EstadoRehen { Retenido, Escoltado, Rescatado }
public sealed record ObjetoVirtual(string Nombre, string Categoria, int Precio);
public sealed class Jugador(string alias, string bando)
{
    private readonly List<ObjetoVirtual> inventario = [];
    public string Alias { get; } = alias;
    public string Bando { get; } = bando;
    public int Creditos { get; private set; } = 1600;
    public int Vida { get; private set; } = 100;
    public int Armadura { get; private set; } = 35;
    public IReadOnlyList<ObjetoVirtual> Inventario => inventario.AsReadOnly();
    public void PrepararRonda() { Vida = 100; Armadura = 35; }
    public void Recompensar() => Creditos += 300;
    public bool Comprar(ObjetoVirtual item)
    {
        // MP9 es solo una ficha del juego: si no alcanza el saldo, no se agrega.
        if (item.Precio < 0 || Creditos < item.Precio) return false;
        Creditos -= item.Precio;
        inventario.Add(item);
        return true;
    }
}
public sealed class Rehen(string nombre, string ubicacion, int integridad, int calmaInicial)
{
    // Un rehén es un NPC del objetivo, no pertenece al plantel de jugadores.
    public string Nombre { get; } = nombre;
    public string Ubicacion { get; } = ubicacion;
    public int Integridad { get; } = integridad;
    public int Calma { get; private set; } = calmaInicial;
    public int Progreso { get; private set; }
    public string Escolta { get; private set; } = "";
    public EstadoRehen Estado { get; private set; } = EstadoRehen.Retenido;
    public void Reiniciar()
    {
        // Conservamos la misma ficha de Elena u Omar, pero restauramos la misión.
        Calma = calmaInicial; Progreso = 0; Escolta = ""; Estado = EstadoRehen.Retenido;
    }
    public void Rescatar(Jugador jugador)
    {
        if (jugador.Bando != "CT" || Estado != EstadoRehen.Retenido)
            throw new InvalidOperationException("Escolta inválida");
        Escolta = jugador.Alias; Calma = Math.Min(100, Calma + 12);
        Estado = EstadoRehen.Escoltado;
        // El progreso representa un estado narrativo, no coordenadas del mapa.
        Progreso = 100; Estado = EstadoRehen.Rescatado;
    }
}
public sealed class Partida(string mapa, Jugador[] ct, Jugador[] t, Rehen[] rehenes)
{
    public string Mapa { get; } = mapa;
    public IReadOnlyList<Jugador> Ct { get; } = ct;
    public IReadOnlyList<Jugador> T { get; } = t;
    public IReadOnlyList<Rehen> Rehenes { get; } = rehenes;
    public Marcador Marcador { get; } = new();
    public List<string> Historial { get; } = [];
    public int Reloj { get; set; } = 115;
    public void IniciarRonda()
    {
        Reloj = 115;
        // Reinicio de personajes; la economía y el marcador sobreviven a la ronda.
        foreach (var jugador in Ct.Concat(T)) jugador.PrepararRonda();
        foreach (var rehen in Rehenes) rehen.Reiniciar();
    }
    public void Registrar(string ganador, string motivo)
    {
        if (ganador == "CT") new SumarCt(Marcador).Ejecutar();
        else Marcador.SumarT();
        // Guardamos la historia como texto antes de reiniciar al NPC.
        Historial.Add($"{ganador}: {motivo}");
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
