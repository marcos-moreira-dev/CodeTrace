// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.DSU;

/// <summary>
/// Disjoint Set Union con path compression y union by size.
/// Costo amortizado O(alfa(n)) casi constante. No almacena rutas.
/// </summary>
public sealed class UnionFind
{
    private readonly int[] _padre,_tam;
    public int Componentes{get;private set;}
    public UnionFind(int n)
    {
        if(n<=0)throw new ArgumentOutOfRangeException(nameof(n));
        Componentes=n;
        _padre=new int[n];_tam=new int[n];
        for(int i=0;i<n;i++){_padre[i]=i;_tam[i]=1;}
    }
    public int Raiz(int x)
    {
        if(_padre[x]!=x)_padre[x]=Raiz(_padre[x]);
        return _padre[x];
    }
    public bool Unir(int a,int b)
    {
        int x=Raiz(a),y=Raiz(b);
        if(x==y)return false;
        if(_tam[x]<_tam[y])(x,y)=(y,x);
        _padre[y]=x;_tam[x]+=_tam[y];Componentes--;
        return true;
    }
    public bool Conectados(int a,int b)=>Raiz(a)==Raiz(b);
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\02-estructuras-datos\union-find\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: UnionFind.cs es un componente; la salida corresponde al cliente MainUnionFind.cs.
 * Salida esperada:
 *   Componentes: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
