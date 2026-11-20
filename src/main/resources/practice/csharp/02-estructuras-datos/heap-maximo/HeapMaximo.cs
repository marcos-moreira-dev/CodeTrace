// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.HeapMaximo;

/// <summary>Heap máximo manual en arreglo. Insertar/extraer O(log n).</summary>
public sealed class HeapMaximo
{
    private readonly List<int> _datos=new();
    public void Insertar(int valor)
    {
        _datos.Add(valor);
        int i=_datos.Count-1;
        while(i>0)
        {
            int p=(i-1)/2;
            if(_datos[p]>=_datos[i])break;
            (_datos[p],_datos[i])=(_datos[i],_datos[p]);i=p;
        }
    }
    public int Extraer()
    {
        if(_datos.Count==0)throw new InvalidOperationException("Heap vacío");
        int max=_datos[0],ultimo=_datos[^1];
        _datos.RemoveAt(_datos.Count-1);
        if(_datos.Count>0)
        {
            _datos[0]=ultimo;
            int i=0;
            while(true)
            {
                int mayor=i,iz=2*i+1,de=iz+1;
                if(iz<_datos.Count&&_datos[iz]>_datos[mayor])mayor=iz;
                if(de<_datos.Count&&_datos[de]>_datos[mayor])mayor=de;
                if(mayor==i)break;
                (_datos[i],_datos[mayor])=(_datos[mayor],_datos[i]);i=mayor;
            }
        }
        return max;
    }
}
