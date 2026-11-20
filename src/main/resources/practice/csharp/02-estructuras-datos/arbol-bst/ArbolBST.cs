// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.BST;

/// <summary>
/// BST genérico sin balanceo. Cada nodo conserva hijos menores y
/// mayores. O(h) insertar/buscar, con peor caso O(n) en entradas
/// ordenadas. Un AVL garantiza altura O(log n) mediante rotaciones.
/// </summary>
public sealed class ArbolBST<T> where T : IComparable<T>
{
    private sealed class Nodo
    {
        public T Dato;
        public Nodo? Izq, Der;
        public Nodo(T dato) => Dato = dato;
    }
    private Nodo? _raiz;

    public void Insertar(T dato) => _raiz = InsertarEn(_raiz, dato);
    private static Nodo InsertarEn(Nodo? nodo, T dato)
    {
        if (nodo is null) return new Nodo(dato);
        int c = dato.CompareTo(nodo.Dato);
        if (c < 0) nodo.Izq = InsertarEn(nodo.Izq, dato);
        else if (c > 0) nodo.Der = InsertarEn(nodo.Der, dato);
        return nodo;
    }
    public IReadOnlyList<T> Inorden()
    {
        var resultado = new List<T>();
        void Visitar(Nodo? n)
        {
            if (n is null) return;
            Visitar(n.Izq);
            resultado.Add(n.Dato);
            Visitar(n.Der);
        }
        Visitar(_raiz);
        return resultado;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\02-estructuras-datos\arbol-bst\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: ArbolBST.cs es un componente; la salida corresponde al cliente MainArbolBST.cs.
 * Salida esperada:
 *   1, 3, 4, 6, 7, 8, 10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
