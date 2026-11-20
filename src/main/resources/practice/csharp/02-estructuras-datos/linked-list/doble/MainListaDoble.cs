// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
using System.Linq;
namespace CodeTrace.Practica.LinkedList.Doble;

/// <summary>
/// Cliente Linked List doble: navegar en dos sentidos y retirar
/// desde la cola en O(1). La referencia anterior consume memoria
/// adicional, pero evita recorrer hasta el penúltimo nodo.
/// </summary>
public static class MainListaDoble
{
    public static void Main()
    {
        var l=new ListaDoble<string>();
        foreach(var x in new[]{"A","B","C"})l.AgregarFinal(x);
        if(!l.Valores().SequenceEqual(new[]{"A","B","C"})||
           !l.Valores(true).SequenceEqual(new[]{"C","B","A"}))
            throw new Exception("Enlaces");
        if(l.QuitarFinal()!="C"||l.Cantidad!=2)throw new Exception("Quitar");
        Console.WriteLine(string.Join(", ",l.Valores()));
    }
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
