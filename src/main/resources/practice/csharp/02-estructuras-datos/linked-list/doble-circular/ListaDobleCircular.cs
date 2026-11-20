// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.LinkedList.DobleCircular;

/// <summary>Lista DOBLE y CIRCULAR: enlaces anterior/siguiente en anillo.</summary>
public sealed class ListaDobleCircular<T>
{
    private sealed class Nodo
    {
        public T Valor;
        public Nodo Anterior,Siguiente;
        public Nodo(T valor){Valor=valor;Anterior=this;Siguiente=this;}
    }
    private Nodo? _ultimo;
    private int _cantidad;
    public void Agregar(T valor)
    {
        var n=new Nodo(valor);
        if(_ultimo is not null)
        {
            var primero=_ultimo.Siguiente;
            n.Anterior=_ultimo;n.Siguiente=primero;
            _ultimo.Siguiente=n;primero.Anterior=n;
        }
        _ultimo=n;_cantidad++;
    }
    public IReadOnlyList<T> Valores(bool inverso=false)
    {
        var r=new List<T>();
        if(_ultimo is null)return r;
        Nodo actual=inverso?_ultimo:_ultimo.Siguiente;
        for(int i=0;i<_cantidad;i++,actual=inverso?actual.Anterior:actual.Siguiente)
            r.Add(actual.Valor);
        return r;
    }
}
