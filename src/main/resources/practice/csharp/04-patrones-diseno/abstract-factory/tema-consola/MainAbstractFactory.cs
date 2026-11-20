// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
namespace CodeTrace.Practica.Patrones.AbstractFactory;

/// <summary>
/// Cliente Abstract Factory: producir DOS piezas relacionadas por tema.
/// A diferencia de Factory Method, una fábrica concreta entrega una
/// familia consistente. Se simula una interfaz con cadenas en consola
/// sin introducir WPF, Windows Forms ni arquitectura MVC.
/// </summary>
public static class MainAbstractFactory
{
    private static string Mostrar(IFabrica f)=>f.Boton().Pintar()+" / "+f.Dialogo().Pintar();
    public static void Main()
    {
        string claro=Mostrar(new FabricaClara()),oscuro=Mostrar(new FabricaOscura());
        if(claro==oscuro||!claro.Contains("clara")||!oscuro.Contains("oscura"))
            throw new Exception("Familias");
        Console.WriteLine(claro);
        Console.WriteLine(oscuro);
    }
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
