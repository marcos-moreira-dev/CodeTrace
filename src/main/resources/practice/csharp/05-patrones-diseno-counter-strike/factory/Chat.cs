// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar: copiar las fuentes de 05 a un proyecto console net8.0 (véase guía).
// Dominio: Counter-Strike — crear avisos de chat de equipo.
// Patrón: Simple Factory.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.CounterStrike;

public interface IMensaje { string Mostrar(); }
public sealed record MensajeChat(string Contenido) : IMensaje
{
    public string Mostrar() => "Chat de equipo: " + Contenido;
}
public static class FabricaChat
{
    // El cliente recibe el contrato, no necesita instanciar MensajeChat.
    public static IMensaje Crear(string contenido) => new MensajeChat(contenido);
    public static void Ejecutar() => Console.WriteLine(Crear("Ronda iniciada").Mostrar());
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP ("ct-cs-"+[guid]::NewGuid())
 *   dotnet new console -f net8.0 -o $d
 *   Remove-Item "$d\Program.cs"
 *   Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cs | Copy-Item -Destination $d
 *   dotnet run --project $d -- factory
 * Salida esperada:
 *   Chat de equipo: Ronda iniciada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
