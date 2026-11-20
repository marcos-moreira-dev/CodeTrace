// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.LinkedList.Circular;

/// <summary>Lista circular con último cuyo siguiente enlaza al primero.</summary>
public sealed class ListaCircular<T>
{
    private sealed class Nodo
    {
        public T Valor;
        public Nodo Siguiente;
        public Nodo(T valor){Valor=valor;Siguiente=this;}
    }
    private Nodo? _ultimo;
    private int _cantidad;
    public void Agregar(T valor)
    {
        var n=new Nodo(valor);
        if(_ultimo is not null)
        {
            n.Siguiente=_ultimo.Siguiente;
            _ultimo.Siguiente=n;
        }
        _ultimo=n;_cantidad++;
    }
    public void Rotar(){if(_ultimo is not null)_ultimo=_ultimo.Siguiente;}
    public IReadOnlyList<T> Valores()
    {
        var r=new List<T>();
        if(_ultimo is null)return r;
        Nodo actual=_ultimo.Siguiente;
        for(int i=0;i<_cantidad;i++,actual=actual.Siguiente)r.Add(actual.Valor);
        return r;
    }
}
