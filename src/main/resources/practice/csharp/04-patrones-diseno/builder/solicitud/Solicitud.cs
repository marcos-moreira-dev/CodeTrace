// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.Patrones.Builder;

/// <summary>
/// Producto de dominio con propiedades de solo lectura. El builder
/// acumula opciones y verifica la coherencia antes de construir.
/// </summary>
public sealed record Solicitud(string Ruta,string Metodo,IReadOnlyList<string> Cabeceras);

public sealed class SolicitudBuilder
{
    private string _ruta="",_metodo="GET";
    private readonly List<string> _cabeceras=new();
    public SolicitudBuilder Ruta(string ruta){_ruta=ruta;return this;}
    public SolicitudBuilder Metodo(string metodo){_metodo=metodo;return this;}
    public SolicitudBuilder Cabecera(string texto){_cabeceras.Add(texto);return this;}
    public Solicitud Construir()
    {
        if(!_ruta.StartsWith("/")||string.IsNullOrWhiteSpace(_metodo))
            throw new InvalidOperationException("Solicitud inválida");
        return new Solicitud(_ruta,_metodo,_cabeceras.ToArray());
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\04-patrones-diseno\builder\solicitud\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: Solicitud.cs es un componente; la salida corresponde al cliente MainBuilder.cs.
 * Salida esperada:
 *   POST /facturas (1 cabecera)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
