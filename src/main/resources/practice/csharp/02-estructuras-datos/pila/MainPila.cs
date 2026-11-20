// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
namespace CodeTrace.Practica.Pila;

/// <summary>
/// Cliente LIFO: comparar una pila redimensionable con una cola FIFO.
/// Pila.cs mantiene invariante 0 &lt;= Cantidad &lt;= Capacidad y
/// hace crecer el almacenamiento por duplicación. La ampliación
/// tiene costo O(n) puntual, pero el costo amortizado por Push
/// a lo largo de muchas operaciones es O(1).
/// </summary>
public static class MainPila
{
    public static void Main()
    {
        var p = new Pila<string>();
        p.Apilar("A"); p.Apilar("B"); p.Apilar("C");
        if (p.Desapilar() != "C" || p.Desapilar() != "B" || p.Cantidad != 1)
            throw new Exception("LIFO incorrecta");
        Console.WriteLine("LIFO verificado");
    }
}

/*
 * Salida esperada de consola:
 *   LIFO verificado
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
