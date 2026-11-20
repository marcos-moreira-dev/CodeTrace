// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
namespace CodeTrace.Practica.ColaCircular;

/// <summary>
/// Cliente: el índice de una cola vuelve al comienzo después de liberar
/// una celda. Una Queue&lt;T&gt; de .NET resuelve este problema en
/// producción; aquí implementamos el arreglo para estudiar los
/// índices y el contrato lleno/vacío.
/// </summary>
public static class MainColaCircular
{
    public static void Main()
    {
        var cola = new ColaCircular<int>(3);
        cola.Encolar(10); cola.Encolar(20); cola.Encolar(30);
        if (cola.Desencolar() != 10) throw new Exception("Orden");
        cola.Encolar(40);
        if (cola.Desencolar() != 20 || cola.Desencolar() != 30 ||
            cola.Desencolar() != 40 || cola.Cantidad != 0) throw new Exception("FIFO");
        Console.WriteLine("Cola circular: prueba de wrap-around OK");
    }
}
