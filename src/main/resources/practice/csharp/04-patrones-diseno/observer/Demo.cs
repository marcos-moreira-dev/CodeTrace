// CodeTrace | Referencia: C# 12+, Observer
// Compilar: dotnet new console; agregar estos .cs; dotnet run
namespace CodeTrace.Observer;
public static class Demo {
    public static void Main() {
        var centro = new CentroEventos();
        centro.Registrar(new ConsolaObservador());
        centro.Publicar("Factura 001");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\04-patrones-diseno\observer\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
