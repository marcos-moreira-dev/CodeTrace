// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.LinkedList.Simple;

/// <summary>Lista simplemente enlazada: cada nodo conoce solo el siguiente.
/// Insertar en cabeza O(1), recorrer O(n), memoria O(n).</summary>
public sealed class ListaSimple<T>
{
    private sealed class Nodo
    {
        public T Valor;
        public Nodo? Siguiente;
        public Nodo(T valor, Nodo? siguiente) { Valor=valor; Siguiente=siguiente; }
    }
    private Nodo? _primero;
    public int Cantidad {get;private set;}
    public void AgregarPrimero(T valor)
    {
        _primero=new Nodo(valor,_primero);
        Cantidad++;
    }
    public IReadOnlyList<T> Valores()
    {
        var r=new List<T>();
        for(var n=_primero;n is not null;n=n.Siguiente)r.Add(n.Valor);
        return r;
    }
}
