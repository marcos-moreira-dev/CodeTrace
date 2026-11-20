// CodeTrace | Referencia: C# 12, .NET 8 SDK.
// Desde PowerShell en una COPIA del paquete:
// Preparar proyecto temporal: dotnet new console -f net8.0 -o "$env:TEMP\codetrace-csharp" --force
// Copiar ejemplo: Copy-Item '.\03-algoritmos\Euclides.cs' "$env:TEMP\codetrace-csharp\Program.cs" -Force
// Compilar: dotnet build "$env:TEMP\codetrace-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\codetrace-csharp"

using System;
Console.WriteLine($"MCD: {Mcd(48, 18)}");
static int Mcd(int a, int b) {
    while (b != 0) {
        (a, b) = (b, a % b);
    }
    return a;
}
