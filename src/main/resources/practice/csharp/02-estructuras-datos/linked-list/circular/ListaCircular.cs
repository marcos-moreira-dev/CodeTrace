// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.LinkedList.Circular;

/// <summary>Lista circular con último cuyo siguiente enlaza al primero.</summary>
public sealed class ListaCircular<T>
{
    private sealed class Nodo
    {
        public T Valor;
        public Nodo Siguiente;
        public Nodo(T valor){Valor=valor;Siguiente=this;}
    }
    private Nodo? _ultimo;
    private int _cantidad;
    public void Agregar(T valor)
    {
        var n=new Nodo(valor);
        if(_ultimo is not null)
        {
            n.Siguiente=_ultimo.Siguiente;
            _ultimo.Siguiente=n;
        }
        _ultimo=n;_cantidad++;
    }
    public void Rotar(){if(_ultimo is not null)_ultimo=_ultimo.Siguiente;}
    public IReadOnlyList<T> Valores()
    {
        var r=new List<T>();
        if(_ultimo is null)return r;
        Nodo actual=_ultimo.Siguiente;
        for(int i=0;i<_cantidad;i++,actual=actual.Siguiente)r.Add(actual.Valor);
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
 *   Copy-Item .\02-estructuras-datos\linked-list\circular\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: ListaCircular.cs es un componente; la salida corresponde al cliente MainListaCircular.cs.
 * Salida esperada:
 *   Luis, Mia, Ana
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
