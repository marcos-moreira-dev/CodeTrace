// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.LinkedList.Doble;

/// <summary>
/// Lista doble: cada nodo posee vínculos anterior/siguiente.
/// Agregar/quitar al final O(1); recorrido bidireccional O(n).
/// </summary>
public sealed class ListaDoble<T>
{
    private sealed class Nodo
    {
        public T Valor;
        public Nodo? Anterior,Siguiente;
        public Nodo(T valor){Valor=valor;}
    }
    private Nodo? _primero,_ultimo;
    public int Cantidad{get;private set;}
    public void AgregarFinal(T valor)
    {
        var n=new Nodo(valor){Anterior=_ultimo};
        if(_ultimo is null)_primero=n;
        else _ultimo.Siguiente=n;
        _ultimo=n;Cantidad++;
    }
    public T QuitarFinal()
    {
        if(_ultimo is null)throw new InvalidOperationException("Vacía");
        var dato=_ultimo.Valor;
        _ultimo=_ultimo.Anterior;
        if(_ultimo is null)_primero=null;
        else _ultimo.Siguiente=null;
        Cantidad--;return dato;
    }
    public IReadOnlyList<T> Valores(bool inverso=false)
    {
        var r=new List<T>();
        for(var n=inverso?_ultimo:_primero;n is not null;
            n=inverso?n.Anterior:n.Siguiente)r.Add(n.Valor);
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
 *   Copy-Item .\02-estructuras-datos\linked-list\doble\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: ListaDoble.cs es un componente; la salida corresponde al cliente MainListaDoble.cs.
 * Salida esperada:
 *   A, B
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
