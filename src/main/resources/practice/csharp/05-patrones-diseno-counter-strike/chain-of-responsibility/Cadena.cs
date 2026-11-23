// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — avisos de rescate, minimapa e inventario virtual.
// Patrón: Chain of Responsibility.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public sealed record Aviso(string Tipo, string Detalle);
public sealed class Manejador(string tipo, string prefijo)
{
    private Manejador? siguiente;
    public Manejador Enlazar(Manejador otro) { siguiente = otro; return otro; }
    public string Atender(Aviso aviso)
    {
        // Cada responsable reconoce una clase de aviso de la ronda.
        // Si no corresponde, el mismo record viaja al siguiente eslabón.
        if (aviso.Tipo == tipo) return $"{prefijo}: {aviso.Detalle}";
        return siguiente?.Atender(aviso) ?? $"Sin responsable: {aviso.Detalle}";
    }
}
public static class Cadena
{
    public static Manejador Crear()
    {
        var primero = new Manejador("rehen", "Rescate");
        primero.Enlazar(new("rival", "Reconocimiento")).Enlazar(new("equipo", "Inventario"));
        return primero;
    }
    public static void Ejecutar()
    {
        var cadena = Crear();
        // Cuatro eventos entran por el mismo responsable, nunca por un switch del cliente.
        Aviso[] avisos = [new("rehen", "rehén virtual liberado"),
            new("rival", "rival marcado en el minimapa"),
            new("equipo", "MP9 registrado como objeto virtual"),
            new("otro", "evento del servidor no reconocido")];
        foreach (var aviso in avisos) Console.WriteLine(cadena.Atender(aviso));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- chain-of-responsibility
 * Salida esperada:
 *   Rescate: rehén virtual liberado
 *   Reconocimiento: rival marcado en el minimapa
 *   Inventario: MP9 registrado como objeto virtual
 *   Sin responsable: evento del servidor no reconocido
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
