// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-sorting" --force; Remove-Item "$env:TEMP\ct-sorting\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-sorting"; dotnet build "$env:TEMP\ct-sorting"
// Ejecutar: dotnet run --project "$env:TEMP\ct-sorting"

namespace CodeTrace.Practica.Algoritmos.Limites;
/// <summary>
/// Lower: primer indice con a[i]>=x. Upper: primero con a[i]>x.
/// Precondicion arreglo ASCENDENTE. Tiempo O(log n), memoria O(1).
/// Devuelven posiciones en [0,n], incluso si x no existe.
/// </summary>
public static class LimitesOrdenados{
    public static int Lower(int[] a,int x){
        int lo=0,hi=a.Length;
        while(lo<hi){
            int m=lo+(hi-lo)/2;
            if(a[m]<x)lo=m+1;else hi=m;
        }
        return lo;
    }
    public static int Upper(int[] a,int x){
        int lo=0,hi=a.Length;
        while(lo<hi){
            int m=lo+(hi-lo)/2;
            if(a[m]<=x)lo=m+1;else hi=m;
        }
        return lo;
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
 *   Copy-Item .\03-algoritmos\busqueda-rangos\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: LimitesOrdenados.cs es un componente; la salida corresponde al cliente MainLimitesOrdenados.cs.
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
