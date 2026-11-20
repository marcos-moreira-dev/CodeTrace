// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-sorting" --force; Remove-Item "$env:TEMP\ct-sorting\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-sorting"; dotnet build "$env:TEMP\ct-sorting"
// Ejecutar: dotnet run --project "$env:TEMP\ct-sorting"

namespace CodeTrace.Practica.Algoritmos.InsercionBinaria;
/// <summary>
/// Binary insertion reduce comparaciones con busqueda por mitades
/// a O(n log n), pero mantiene desplazamientos O(n²) de arreglos.
/// Estable al insertar despues de claves iguales; O(1) auxiliar.
/// </summary>
public static class InsercionBinaria{
    public static void Ordenar(int[] a){
        for(int i=1;i<a.Length;i++){
            int clave=a[i],lo=0,hi=i;
            while(lo<hi){
                int m=lo+(hi-lo)/2;
                if(a[m]<=clave)lo=m+1;else hi=m;
            }
            for(int j=i;j>lo;j--)a[j]=a[j-1];
            a[lo]=clave;
        }
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
 *   Copy-Item .\03-algoritmos\ordenamientos\insercion-binaria\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: InsercionBinaria.cs es un componente; la salida corresponde al cliente MainInsercionBinaria.cs.
 * Salida esperada:
 *   Insercion binaria: [1, 2, 3, 3, 5]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
