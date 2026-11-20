// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

using System;
namespace CodeTrace.Practica.FactoryMethod;

/// <summary>
/// Cliente Factory Method: NO confundir con una simple función switch.
/// El creador abstracto define el método de fábrica, una subclase
/// concreta decide qué producto fabricar y el flujo Preparar se
/// comparte entre creadores. Contrato y producto pueden evolucionar.
/// </summary>
public static class MainFactoryMethod
{
    public static void Main()
    {
        Creador creador = new CreadorTexto();
        string salida = creador.Preparar("Factura 001");
        if (salida != "TXT: Factura 001") throw new Exception("Fábrica");
        Console.WriteLine(salida);
    }
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
