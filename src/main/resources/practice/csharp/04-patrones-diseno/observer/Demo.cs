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
