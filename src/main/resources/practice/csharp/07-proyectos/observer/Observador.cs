// CodeTrace | Referencia: C# 12+, Observer
// Compilar: dotnet new console; agregar estos .cs; dotnet run
namespace CodeTrace.Observer;
public interface IObservador { void Actualizar(string evento); }
public sealed class ConsolaObservador : IObservador {
    public void Actualizar(string evento) => System.Console.WriteLine(evento);
}
