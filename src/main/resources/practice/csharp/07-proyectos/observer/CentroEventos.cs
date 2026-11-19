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
