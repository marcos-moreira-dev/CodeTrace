// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
namespace CodeTrace.Practica.Patrones.TemplateMethod;

/// <summary>
/// Cliente Template Method: una clase base fija el flujo de trabajo
/// y las subclases cambian los pasos protegidos. Strategy selecciona
/// un comportamiento a través de composición: aquí se usa herencia.
/// Ambos problemas se demuestran con programas de consola.
/// </summary>
public static class MainTemplateMethod
{
    public static void Main()
    {
        ProcesoReporte a=new ReporteTexto(),b=new ReporteCsv();
        if(!a.Ejecutar("ventas",15).Contains("Total: 15")||
           !b.Ejecutar("ventas",15).Contains("ventas,15"))
            throw new Exception("Template Method");
        Console.WriteLine(a.Ejecutar("ventas",15));
        Console.WriteLine(b.Ejecutar("ventas",15));
    }
}
