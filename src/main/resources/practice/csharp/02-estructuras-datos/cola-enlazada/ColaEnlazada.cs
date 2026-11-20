// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.ColaEnlazada;

/// <summary>FIFO por nodos, cabeza y cola; encolar/desencolar O(1).</summary>
public sealed class ColaEnlazada<T>
{
    private sealed class Nodo
    {
        public T Valor;
        public Nodo? Siguiente;
        public Nodo(T valor){Valor=valor;}
    }
    private Nodo? _primero,_ultimo;
    public int Cantidad{get;private set;}
    public void Encolar(T valor)
    {
        var n=new Nodo(valor);
        if(_ultimo is null)_primero=n;
        else _ultimo.Siguiente=n;
        _ultimo=n;Cantidad++;
    }
    public T Desencolar()
    {
        if(_primero is null)throw new InvalidOperationException("Cola vacía");
        var valor=_primero.Valor;
        _primero=_primero.Siguiente;
        if(_primero is null)_ultimo=null;
        Cantidad--;return valor;
    }
}
