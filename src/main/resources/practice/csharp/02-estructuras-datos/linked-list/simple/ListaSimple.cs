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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\02-estructuras-datos\linked-list\simple\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: ListaSimple.cs es un componente; la salida corresponde al cliente MainListaSimple.cs.
 * Salida esperada:
 *   C, B, A
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
