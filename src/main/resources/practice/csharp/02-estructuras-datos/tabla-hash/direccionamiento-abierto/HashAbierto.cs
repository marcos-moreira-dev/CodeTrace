// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.HashAbierto;

/// <summary>
/// Hash por sondeo lineal y capacidad fija. Sin eliminación:
/// requiere tombstones para no interrumpir búsquedas. Promedio O(1)
/// con baja carga; peor O(n) si hay muchas colisiones.
/// </summary>
public sealed class HashAbierto
{
    private readonly string?[] _claves;
    private readonly int[] _valores;
    public int Cantidad{get;private set;}
    public HashAbierto(int capacidad)
    {
        if(capacidad<=0)throw new ArgumentOutOfRangeException(nameof(capacidad));
        _claves=new string[capacidad];_valores=new int[capacidad];
    }
    private int Indice(string clave)=>(clave.GetHashCode()&int.MaxValue)%_claves.Length;
    public void Poner(string clave,int valor)
    {
        int ini=Indice(clave);
        for(int paso=0;paso<_claves.Length;paso++)
        {
            int i=(ini+paso)%_claves.Length;
            if(_claves[i] is null)
            {
                _claves[i]=clave;_valores[i]=valor;Cantidad++;return;
            }
            if(_claves[i]==clave){_valores[i]=valor;return;}
        }
        throw new InvalidOperationException("Tabla llena");
    }
    public int? Buscar(string clave)
    {
        int ini=Indice(clave);
        for(int paso=0;paso<_claves.Length;paso++)
        {
            int i=(ini+paso)%_claves.Length;
            if(_claves[i] is null)return null;
            if(_claves[i]==clave)return _valores[i];
        }
        return null;
    }
}
