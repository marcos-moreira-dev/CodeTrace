// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.Hash;

/// <summary>
/// Tabla hash didáctica con encadenamiento separado.
/// HashCode se reduce a un índice positivo; dos claves pueden chocar
/// y deben compararse mediante igualdad real. Esperado O(1), peor O(n).
/// No hay redimensionado: no sustituye Dictionary de producción.
/// </summary>
public sealed class TablaHash<T>
{
    private readonly List<KeyValuePair<string, T>>[] _cubetas;
    public TablaHash(int capacidad)
    {
        if (capacidad < 1) throw new ArgumentOutOfRangeException(nameof(capacidad));
        _cubetas = new List<KeyValuePair<string, T>>[capacidad];
        for (int i = 0; i < capacidad; i++) _cubetas[i] = new();
    }
    private int Indice(string clave) => (clave.GetHashCode() & int.MaxValue) % _cubetas.Length;

    public void Poner(string clave, T valor)
    {
        var bucket = _cubetas[Indice(clave)];
        for (int i = 0; i < bucket.Count; i++)
            if (bucket[i].Key == clave)
            {
                bucket[i] = new KeyValuePair<string, T>(clave, valor);
                return;
            }
        bucket.Add(new KeyValuePair<string, T>(clave, valor));
    }
    public bool Buscar(string clave, out T valor)
    {
        foreach (var par in _cubetas[Indice(clave)])
            if (par.Key == clave) { valor = par.Value; return true; }
        valor = default!;
        return false;
    }
}
