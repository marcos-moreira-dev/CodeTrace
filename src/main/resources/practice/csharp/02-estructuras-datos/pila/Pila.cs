// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
namespace CodeTrace.Practica.Pila;

/// <summary>
/// Pila LIFO redimensionable con arreglo propio.
/// Push O(1) amortizado (una ampliación cuesta O(n)); Pop O(1).
/// Espacio O(capacidad). Se limpian referencias al extraer.
/// </summary>
public sealed class Pila<T>
{
    private T[] _datos = new T[4];
    public int Cantidad { get; private set; }
    public void Apilar(T valor)
    {
        if (Cantidad == _datos.Length) Array.Resize(ref _datos, _datos.Length * 2);
        _datos[Cantidad++] = valor;
    }
    public T Desapilar()
    {
        if (Cantidad == 0) throw new InvalidOperationException("Pila vacía");
        var valor = _datos[--Cantidad];
        _datos[Cantidad] = default!;
        return valor;
    }
}
