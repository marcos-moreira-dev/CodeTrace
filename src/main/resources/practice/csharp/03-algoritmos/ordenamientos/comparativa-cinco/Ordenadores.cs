// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-sorting" --force; Remove-Item "$env:TEMP\ct-sorting\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-sorting"; dotnet build "$env:TEMP\ct-sorting"
// Ejecutar: dotnet run --project "$env:TEMP\ct-sorting"

using System;
namespace CodeTrace.Practica.Algoritmos.Sort;

/// <summary>
/// Cinco ordenamientos clasicos: Insertion/Bubble estable y O(n²)
/// peor caso; Merge O(n log n) O(n) memoria y estable;
/// Heap O(n log n) in-place no estable; Quick O(n²) peor con
/// pivote final, no estable. No comparar solo milisegundos.
/// </summary>
public static class Ordenadores{
    public static void Insercion(int[] a){
        for(int i=1;i<a.Length;i++){
            int x=a[i],j=i-1;
            while(j>=0&&a[j]>x){a[j+1]=a[j];j--;}
            a[j+1]=x;
        }
    }
    public static void Burbuja(int[] a){
        int limite=a.Length;
        while(limite>1){
            int ultimo=0;
            for(int j=1;j<limite;j++)if(a[j-1]>a[j]){
                (a[j-1],a[j])=(a[j],a[j-1]);ultimo=j;
            }
            limite=ultimo;
        }
    }
    public static void Mezcla(int[] a){
        var tmp=new int[a.Length];
        void Sort(int lo,int hi){
            if(hi-lo<=1)return;
            int m=lo+(hi-lo)/2;Sort(lo,m);Sort(m,hi);
            int i=lo,j=m;
            for(int k=lo;k<hi;k++){
                if(j==hi||(i<m&&a[i]<=a[j]))tmp[k]=a[i++];
                else tmp[k]=a[j++];
            }
            Array.Copy(tmp,lo,a,lo,hi-lo);
        }
        Sort(0,a.Length);
    }
    public static void Heap(int[] a){
        void Hundir(int n,int inicio){
            int i=inicio;
            while(true){
                int mayor=i,iz=2*i+1,de=iz+1;
                if(iz<n&&a[iz]>a[mayor])mayor=iz;
                if(de<n&&a[de]>a[mayor])mayor=de;
                if(mayor==i)return;
                (a[i],a[mayor])=(a[mayor],a[i]);i=mayor;
            }
        }
        for(int i=a.Length/2-1;i>=0;i--)Hundir(a.Length,i);
        for(int fin=a.Length-1;fin>0;fin--){
            (a[0],a[fin])=(a[fin],a[0]);Hundir(fin,0);
        }
    }
    public static void Rapido(int[] a){
        void Sort(int lo,int hi){
            if(lo>=hi)return;
            int p=a[hi],i=lo;
            for(int j=lo;j<hi;j++)if(a[j]<=p){
                (a[i],a[j])=(a[j],a[i]);i++;
            }
            (a[i],a[hi])=(a[hi],a[i]);
            Sort(lo,i-1);Sort(i+1,hi);
        }
        Sort(0,a.Length-1);
    }
}
