// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
namespace CodeTrace.Practica.ColaCircular;

/// <summary>
/// Cola FIFO de capacidad fija, implementada con arreglo circular.
/// Índices se actualizan módulo capacidad: Encolar/Desencolar O(1),
/// sin desplazar elementos. Se distingue lleno y vacío con Cantidad.
/// </summary>
public sealed class ColaCircular<T>
{
    private readonly T[] _datos;
    private int _inicio, _siguiente;
    public int Cantidad { get; private set; }
    public ColaCircular(int capacidad)
    {
        if (capacidad < 1) throw new ArgumentOutOfRangeException(nameof(capacidad));
        _datos = new T[capacidad];
    }
    public void Encolar(T dato)
    {
        if (Cantidad == _datos.Length) throw new InvalidOperationException("Cola llena");
        _datos[_siguiente] = dato;
        _siguiente = (_siguiente + 1) % _datos.Length;
        Cantidad++;
    }
    public T Desencolar()
    {
        if (Cantidad == 0) throw new InvalidOperationException("Cola vacía");
        var dato = _datos[_inicio];
        _datos[_inicio] = default!;
        _inicio = (_inicio + 1) % _datos.Length;
        Cantidad--;
        return dato;
    }
}
