// CodeTrace | Referencia: C# 12, .NET 8 SDK.
// Desde PowerShell en una COPIA del paquete:
// Preparar proyecto temporal: dotnet new console -f net8.0 -o "$env:TEMP\codetrace-csharp" --force
// Copiar ejemplo: Copy-Item '.\04-patrones-diseno\Observer.cs' "$env:TEMP\codetrace-csharp\Program.cs" -Force
// Compilar: dotnet build "$env:TEMP\codetrace-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\codetrace-csharp"

using System;
var emisor = new Emisor();
emisor.Cambio += valor => Console.WriteLine($"Cambio: {valor}");
emisor.Actualizar(42);
public sealed class Emisor {
    public event Action<int>? Cambio;
    public void Actualizar(int valor) => Cambio?.Invoke(valor);
}
