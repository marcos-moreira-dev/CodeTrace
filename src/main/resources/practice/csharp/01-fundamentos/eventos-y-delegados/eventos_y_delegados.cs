// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar/Comprobar: dotnet new console -o $env:TEMP\ct-csharp; Copy-Item ARCHIVO.cs $env:TEMP\ct-csharp\Program.cs -Force; dotnet build $env:TEMP\ct-csharp
// Ejecutar: dotnet run --project $env:TEMP\ct-csharp

using System;
/**
 * Cliente de delegados y eventos en C#.
 * Un delegate tipa una función. Un event permite a otros componentes
 * suscribirse sin poder emitir desde fuera; es un mecanismo idiomático
 * para el patrón Observer. No se necesitan frameworks de interfaz.
 */
public sealed class Sensor
{
    public event Action<int>? NuevaLectura;
    public void Emitir(int lectura) => NuevaLectura?.Invoke(lectura);
}
public static class Program
{
    public static void Main()
    {
        var sensor = new Sensor();
        sensor.NuevaLectura += valor => Console.WriteLine("Lectura: " + valor);
        sensor.Emitir(28);
    }
}
