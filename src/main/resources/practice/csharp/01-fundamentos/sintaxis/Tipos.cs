// CodeTrace | Referencia: C# 12 y .NET SDK 8.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Compilar/verificar: dotnet new console -f net8.0 -o "$env:TEMP\codetrace-csharp" --force; Copy-Item '.\01-sintaxis\Tipos.cs' "$env:TEMP\codetrace-csharp\Program.cs" -Force; dotnet build "$env:TEMP\codetrace-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\codetrace-csharp"

using System;

int cantidad = 3;
decimal precio = 12.5m;
decimal total = cantidad * precio;
Console.WriteLine(total);
