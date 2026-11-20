// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en copia desde esta carpeta: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Quitar Program.cs temporal; copiar los *.cs de esta unidad y ejecutar dotnet run.
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Una unidad tiene Main... + dominio; no ejecutar el .cs de implementación solo.

using System;
namespace CodeTrace.Practica.Grafo;

/// <summary>
/// Cliente de grafo con latencias entre tres servidores. Una arista
/// dirigida no representa una relación bidireccional. La estructura
/// es independiente del algoritmo que luego recorrerá sus nodos.
/// Con O(V+E) espacio evita reservar una matriz V por V dispersa.
/// </summary>
public static class MainGrafo
{
    public static void Main()
    {
        var g = new Grafo();
        g.Conectar("GYE", "UIO", 12);
        g.Conectar("GYE", "CUE", 18);
        g.Conectar("UIO", "CUE", 8);
        if (g.Vecinos("GYE").Count != 2 || g.Vecinos("CUE").Count != 0)
            throw new Exception("Dirección de aristas");
        Console.WriteLine("Adyacencias GYE: " + g.Vecinos("GYE").Count);
    }
}

/*
 * Salida esperada de consola:
 *   Adyacencias GYE: 
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
