// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.LinkedList.DobleCircular;

/// <summary>Lista DOBLE y CIRCULAR: enlaces anterior/siguiente en anillo.</summary>
public sealed class ListaDobleCircular<T>
{
    private sealed class Nodo
    {
        public T Valor;
        public Nodo Anterior,Siguiente;
        public Nodo(T valor){Valor=valor;Anterior=this;Siguiente=this;}
    }
    private Nodo? _ultimo;
    private int _cantidad;
    public void Agregar(T valor)
    {
        var n=new Nodo(valor);
        if(_ultimo is not null)
        {
            var primero=_ultimo.Siguiente;
            n.Anterior=_ultimo;n.Siguiente=primero;
            _ultimo.Siguiente=n;primero.Anterior=n;
        }
        _ultimo=n;_cantidad++;
    }
    public IReadOnlyList<T> Valores(bool inverso=false)
    {
        var r=new List<T>();
        if(_ultimo is null)return r;
        Nodo actual=inverso?_ultimo:_ultimo.Siguiente;
        for(int i=0;i<_cantidad;i++,actual=inverso?actual.Anterior:actual.Siguiente)
            r.Add(actual.Valor);
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
 *   Copy-Item .\02-estructuras-datos\linked-list\doble-circular\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: ListaDobleCircular.cs es un componente; la salida corresponde al cliente MainListaDobleCircular.cs.
 * Salida esperada:
 *   2, 3, 5
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
