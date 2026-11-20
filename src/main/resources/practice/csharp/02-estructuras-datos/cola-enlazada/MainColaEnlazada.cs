// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.ColaEnlazada;

/// <summary>
/// Cliente cola enlazada frente al buffer circular de capacidad fija.
/// En ambos casos FIFO y O(1); aquí se reserva un nuevo nodo por
/// elemento. Al vaciar debe reiniciarse también el puntero final.
/// </summary>
public static class MainColaEnlazada
{
    public static void Main()
    {
        var q=new ColaEnlazada<string>();
        q.Encolar("A");q.Encolar("B");
        if(q.Desencolar()!="A"||q.Desencolar()!="B")throw new Exception("FIFO");
        q.Encolar("C");
        if(q.Desencolar()!="C"||q.Cantidad!=0)throw new Exception("Tail");
        Console.WriteLine("FIFO enlazada verificada");
    }
}
