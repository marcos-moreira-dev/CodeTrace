// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.Deque;

/// <summary>
/// Deque mediante LinkedList de .NET: operaciones O(1) en extremos.
/// El caso manual de nodos se estudia en Linked List doble.
/// </summary>
public sealed class DequeCurso<T>
{
    private readonly LinkedList<T> _datos=new();
    public void AgregarPrimero(T v)=>_datos.AddFirst(v);
    public void AgregarUltimo(T v)=>_datos.AddLast(v);
    public T QuitarPrimero()
    {
        if(_datos.First is null)throw new InvalidOperationException("Deque vacío");
        var v=_datos.First.Value;_datos.RemoveFirst();return v;
    }
    public T QuitarUltimo()
    {
        if(_datos.Last is null)throw new InvalidOperationException("Deque vacío");
        var v=_datos.Last.Value;_datos.RemoveLast();return v;
    }
}
