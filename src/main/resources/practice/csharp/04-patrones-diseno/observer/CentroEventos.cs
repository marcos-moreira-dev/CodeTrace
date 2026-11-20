// CodeTrace | Referencia: C# 12+, Observer
// Compilar: dotnet new console; agregar estos .cs; dotnet run
namespace CodeTrace.Observer;
public sealed class CentroEventos {
    private readonly System.Collections.Generic.List<IObservador> observadores = new();
    public void Registrar(IObservador observador) => observadores.Add(observador);
    public void Publicar(string evento) {
        foreach (var observador in observadores) observador.Actualizar(evento);
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
 * Nota: CentroEventos.cs es un componente; la salida corresponde al cliente Demo.cs.
 * Salida esperada:
 *   Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
