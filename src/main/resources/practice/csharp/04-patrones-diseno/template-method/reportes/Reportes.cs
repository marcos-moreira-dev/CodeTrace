// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
namespace CodeTrace.Practica.Patrones.TemplateMethod;

/// <summary>
/// Template Method: Ejecutar fija validación y secuencia de pasos.
/// No se marca virtual, por lo que las subclases no pueden alterar
/// polimórficamente el método plantilla. Los pasos sí son abstractos.
/// </summary>
public abstract class ProcesoReporte
{
    public string Ejecutar(string titulo,int total)
    {
        if(string.IsNullOrWhiteSpace(titulo)||total<0)
            throw new ArgumentException("Reporte inválido");
        return Encabezado(titulo)+Detalle(total)+Pie();
    }
    protected abstract string Encabezado(string titulo);
    protected abstract string Detalle(int total);
    protected virtual string Pie()=>"\n";
}
public sealed class ReporteTexto:ProcesoReporte
{
    protected override string Encabezado(string t)=>$"== {t} ==\n";
    protected override string Detalle(int n)=>$"Total: {n}";
}
public sealed class ReporteCsv:ProcesoReporte
{
    protected override string Encabezado(string t)=>"titulo,total\n";
    protected override string Detalle(int n)=>$"ventas,{n}";
}
