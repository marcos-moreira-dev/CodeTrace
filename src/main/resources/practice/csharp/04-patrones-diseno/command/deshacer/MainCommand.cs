// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
using System.Collections.Generic;
namespace CodeTrace.Practica.Patrones.Command;

/// <summary>
/// Cliente Command: desacoplar invocador de receptor, conservar
/// comandos ejecutados y aplicar deshacer LIFO. Stack&lt;IComando&gt;
/// es una colección estándar de .NET. La interfaz no es solamente
/// un callback, pues almacena estado reversible.
/// </summary>
public static class MainCommand
{
    public static void Main()
    {
        var e=new Editor();
        var historia=new Stack<IComando>();
        foreach(string parte in new[]{"Hola"," mundo"})
        {
            IComando c=new AgregarTexto(e,parte);
            c.Ejecutar();historia.Push(c);
        }
        if(e.Texto!="Hola mundo")throw new Exception("Ejecución");
        historia.Pop().Deshacer();
        if(e.Texto!="Hola")throw new Exception("Deshacer");
        Console.WriteLine(e.Texto);
    }
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
