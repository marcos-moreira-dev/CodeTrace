// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar desde COPIA: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-patron" --force; Remove-Item "$env:TEMP\ct-cs-patron\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-patron"; dotnet build "$env:TEMP\ct-cs-patron"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-patron"

namespace CodeTrace.Practica.Patrones.AbstractFactory;

/// <summary>Familia con dos contratos de productos.</summary>
public interface IBoton{string Pintar();}
public interface IDialogo{string Pintar();}
public interface IFabrica{IBoton Boton();IDialogo Dialogo();}

public sealed class Texto:IBoton,IDialogo
{
    private readonly string _texto;
    public Texto(string texto)=>_texto=texto;
    public string Pintar()=>_texto;
}
public sealed class FabricaClara:IFabrica
{
    public IBoton Boton()=>new Texto("[ OK ]");
    public IDialogo Dialogo()=>new Texto("Ventana clara");
}
public sealed class FabricaOscura:IFabrica
{
    public IBoton Boton()=>new Texto("< OK >");
    public IDialogo Dialogo()=>new Texto("Ventana oscura");
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\04-patrones-diseno\abstract-factory\tema-consola\*.cs $d -Force
 *   dotnet run --project $d
 * Nota: Familias.cs es un componente; la salida corresponde al cliente MainAbstractFactory.cs.
 * Salida esperada:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
