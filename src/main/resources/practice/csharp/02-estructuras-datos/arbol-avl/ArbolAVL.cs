// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.AVL;

/// <summary>
/// AVL: árbol BST que restaura balance [-1,+1] por rotaciones.
/// Inserción O(log n); la altura es logarítmica incluso con
/// entradas ascendentes, a diferencia de un BST sin balancear.
/// </summary>
public sealed class ArbolAVL
{
    private sealed class Nodo
    {
        public int Valor,Altura=1;
        public Nodo? Izq,Der;
        public Nodo(int valor){Valor=valor;}
    }
    private Nodo? _raiz;
    private static int Altura(Nodo? n)=>n?.Altura??0;
    private static void Actualizar(Nodo n)=>n.Altura=1+Math.Max(Altura(n.Izq),Altura(n.Der));
    private static Nodo Derecha(Nodo y)
    {
        var x=y.Izq!;
        y.Izq=x.Der;x.Der=y;
        Actualizar(y);Actualizar(x);return x;
    }
    private static Nodo Izquierda(Nodo x)
    {
        var y=x.Der!;
        x.Der=y.Izq;y.Izq=x;
        Actualizar(x);Actualizar(y);return y;
    }
    private static Nodo Insertar(Nodo? n,int dato)
    {
        if(n is null)return new Nodo(dato);
        if(dato<n.Valor)n.Izq=Insertar(n.Izq,dato);
        else if(dato>n.Valor)n.Der=Insertar(n.Der,dato);
        else return n;
        Actualizar(n);
        int b=Altura(n.Izq)-Altura(n.Der);
        if(b>1)
        {
            if(dato>n.Izq!.Valor)n.Izq=Izquierda(n.Izq!);
            return Derecha(n);
        }
        if(b< -1)
        {
            if(dato<n.Der!.Valor)n.Der=Derecha(n.Der!);
            return Izquierda(n);
        }
        return n;
    }
    public void Agregar(int dato)=>_raiz=Insertar(_raiz,dato);
    public IReadOnlyList<int> Inorden()
    {
        var r=new List<int>();
        void Visitar(Nodo? n)
        {
            if(n is null)return;
            Visitar(n.Izq);r.Add(n.Valor);Visitar(n.Der);
        }
        Visitar(_raiz);return r;
    }
    public int Validar()
    {
        int Recorrer(Nodo? n)
        {
            if(n is null)return 0;
            int a=Recorrer(n.Izq),b=Recorrer(n.Der);
            if(Math.Abs(a-b)>1||n.Altura!=1+Math.Max(a,b))
                throw new InvalidOperationException("Desbalance");
            return n.Altura;
        }
        return Recorrer(_raiz);
    }
}
