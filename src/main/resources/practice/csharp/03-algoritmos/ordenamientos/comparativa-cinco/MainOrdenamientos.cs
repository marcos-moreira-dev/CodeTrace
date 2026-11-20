// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-sorting" --force; Remove-Item "$env:TEMP\ct-sorting\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-sorting"; dotnet build "$env:TEMP\ct-sorting"
// Ejecutar: dotnet run --project "$env:TEMP\ct-sorting"

using System;
using System.Linq;
namespace CodeTrace.Practica.Algoritmos.Sort;

/// <summary>
/// Cliente: comparativa funcional de cinco sorts con COPIAS de
/// una entrada identica, duplicados y arreglo vacio. Esto no
/// demuestra complejidad: hay que examinar invariantes, memoria
/// auxiliar, estabilidad y escenarios adversos.
/// </summary>
public static class MainOrdenamientos{
    public static void Main(){
        var sorts=new (string Nombre,Action<int[]> Fn)[]{
            ("Insercion",Ordenadores.Insercion),("Burbuja",Ordenadores.Burbuja),
            ("Mezcla",Ordenadores.Mezcla),("HeapSort",Ordenadores.Heap),
            ("QuickSort",Ordenadores.Rapido)
        };
        foreach(var (nombre,fn) in sorts){
            int[] a={9,3,8,1,7,2,5};fn(a);
            if(!a.SequenceEqual(new[]{1,2,3,5,7,8,9}))
                throw new Exception(nombre);
            Console.WriteLine(nombre+": ["+string.Join(", ",a)+"]");
            int[] vacio=Array.Empty<int>();
            int[] repetidos={3,1,3,1};
            fn(vacio);fn(repetidos);
            if(!repetidos.SequenceEqual(new[]{1,1,3,3}))
                throw new Exception("Limites");
        }
    }
}
/*
 * Salida esperada:
 * Insercion: [1, 2, 3, 5, 7, 8, 9]
 * Burbuja: [1, 2, 3, 5, 7, 8, 9]
 * Mezcla: [1, 2, 3, 5, 7, 8, 9]
 * HeapSort: [1, 2, 3, 5, 7, 8, 9]
 * QuickSort: [1, 2, 3, 5, 7, 8, 9]
 */
