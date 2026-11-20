// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
using System.Collections.Generic;
using System.Linq;
namespace CodeTrace.Practica.Patrones.Composite;

/// <summary>Interfaz común para hojas y compuestos.</summary>
public interface IElemento{int Bytes();}

public sealed record Archivo(string Nombre,int Tam):IElemento
{
    public int Bytes()
    {
        if(Tam<0)throw new ArgumentOutOfRangeException(nameof(Tam));
        return Tam;
    }
}
public sealed class Carpeta:IElemento
{
    private readonly List<IElemento> _hijos=new();
    public string Nombre{get;}
    public Carpeta(string nombre)=>Nombre=nombre;
    public void Agregar(IElemento hijo)
    {
        if(ReferenceEquals(hijo,this))throw new ArgumentException("Ciclo directo");
        _hijos.Add(hijo);
    }
    public int Bytes()=>_hijos.Sum(x=>x.Bytes());
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\04-patrones-diseno\composite\arbol-archivos\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: Elementos.cs es un componente; la salida corresponde al cliente MainComposite.cs.
 * Salida esperada:
 *   Bytes: 220
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
