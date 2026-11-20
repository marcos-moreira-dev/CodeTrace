// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar/Comprobar: dotnet new console -o $env:TEMP\ct-csharp; Copy-Item 03-algoritmos/busqueda-binaria/busqueda_binaria.cs $env:TEMP\ct-csharp\Program.cs -Force; dotnet build $env:TEMP\ct-csharp
// Ejecutar: dotnet run --project $env:TEMP\ct-csharp

using System;
/**
 * Cliente de búsqueda binaria: el arreglo debe estar ordenado.
 * Mantiene un intervalo [inicio, fin) y descarta aproximadamente
 * media región en cada paso. O(log n) tiempo y O(1) espacio.
 */
public static class Program
{
    private static int Buscar(int[] a, int objetivo)
    {
        int inicio = 0, fin = a.Length;
        while (inicio < fin)
        {
            int mitad = inicio + (fin - inicio) / 2;
            if (a[mitad] == objetivo) return mitad;
            if (a[mitad] < objetivo) inicio = mitad + 1;
            else fin = mitad;
        }
        return -1;
    }
    public static void Main()
    {
        Console.WriteLine(Buscar(new[] { 2, 4, 9, 15, 20 }, 15));
    }
}
