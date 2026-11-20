// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-sorting" --force; Remove-Item "$env:TEMP\ct-sorting\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-sorting"; dotnet build "$env:TEMP\ct-sorting"
// Ejecutar: dotnet run --project "$env:TEMP\ct-sorting"

using System;
namespace CodeTrace.Practica.Algoritmos.Limites;
/// <summary>
/// Cliente lower/upper bound: valores 3 en [1,4) y valor 4
/// inexistente con posicion de insercion 4. No ordenar aqui.
/// </summary>
public static class MainLimitesOrdenados{
    public static void Main(){
        int[] a={1,3,3,3,5,9};
        int lo=LimitesOrdenados.Lower(a,3),hi=LimitesOrdenados.Upper(a,3);
        int pos=LimitesOrdenados.Lower(a,4);
        if(lo!=1||hi!=4||pos!=4||LimitesOrdenados.Upper(a,4)!=4)
            throw new Exception("Rangos");
        Console.WriteLine("Valor 3: ["+lo+", "+hi+")");
        Console.WriteLine("Insertar 4 en indice: "+pos);
    }
}
/*
 * Salida esperada:
 * Valor 3: [1, 4)
 * Insertar 4 en indice: 4
 */
