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
