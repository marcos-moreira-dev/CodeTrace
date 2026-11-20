// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
namespace CodeTrace.Practica.Patrones.Builder;

/// <summary>
/// Cliente Builder: separar pasos de construcción y producto final.
/// Los argumentos nombrados de C# pueden bastar con pocos campos;
/// aquí la configuración por etapas y validación justifican Builder.
/// Un record ofrece igualdad por valor y propiedades init-only.
/// </summary>
public static class MainBuilder
{
    public static void Main()
    {
        var s=new SolicitudBuilder().Ruta("/facturas").Metodo("POST")
            .Cabecera("Content-Type: application/json").Construir();
        if(s.Ruta!="/facturas"||s.Cabeceras.Count!=1)
            throw new Exception("Builder");
        Console.WriteLine(s);
    }
}

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
