// CodeTrace | Referencia: C# 12 / .NET 8
// Preparar en COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-unit" --force
// Compilar: Remove-Item "$env:TEMP\ct-cs-unit\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-unit"; dotnet build "$env:TEMP\ct-cs-unit"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-unit"
// Se compilan juntas las clases de una carpeta de unidad, no toda la biblioteca.

namespace CodeTrace.Practica.FactoryMethod;

/// <summary>Producto: contrato que no depende del formato concreto.</summary>
public interface IExportador
{
    string Exportar(string documento);
}
public sealed class ExportadorTexto : IExportador
{
    public string Exportar(string documento) => "TXT: " + documento;
}

/// <summary>
/// Creador GoF: la subclase decide qué producto construir. Preparar
/// contiene lógica común y llama a Crear() como punto de extensión.
/// </summary>
public abstract class Creador
{
    protected abstract IExportador Crear();
    public string Preparar(string texto) => Crear().Exportar(texto);
}
public sealed class CreadorTexto : Creador
{
    protected override IExportador Crear() => new ExportadorTexto();
}
