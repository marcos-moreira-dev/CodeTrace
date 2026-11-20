// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

using System;
namespace CodeTrace.Practica.Patrones.Command;

/// <summary>Contrato de una operación reversible (Command).</summary>
public interface IComando{void Ejecutar();void Deshacer();}

/// <summary>Receptor: representa el texto que cambia.</summary>
public sealed class Editor
{
    public string Texto{get;private set;}="";
    public void Agregar(string parte)=>Texto+=parte;
    public void Restaurar(string estado)=>Texto=estado;
}

/// <summary>Comando concreto que conserva su estado anterior para undo.</summary>
public sealed class AgregarTexto:IComando
{
    private readonly Editor _editor;
    private readonly string _parte;
    private string? _anterior;
    public AgregarTexto(Editor editor,string parte){_editor=editor;_parte=parte;}
    public void Ejecutar()
    {
        if(_anterior is not null)throw new InvalidOperationException("Ya ejecutado");
        _anterior=_editor.Texto;_editor.Agregar(_parte);
    }
    public void Deshacer()
    {
        if(_anterior is null)throw new InvalidOperationException("No ejecutado");
        _editor.Restaurar(_anterior);_anterior=null;
    }
}
