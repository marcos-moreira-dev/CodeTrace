// CodeTrace | Referencia: C# 12, .NET 8 SDK.
// Desde PowerShell en una COPIA del paquete:
// Preparar proyecto temporal: dotnet new console -f net8.0 -o "$env:TEMP\codetrace-csharp" --force
// Copiar ejemplo: Copy-Item '.\05-algoritmos\BusquedaBinaria.cs' "$env:TEMP\codetrace-csharp\Program.cs" -Force
// Compilar: dotnet build "$env:TEMP\codetrace-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\codetrace-csharp"

using System;
int[] ordenados = { 2, 4, 8, 12, 16 };
int indice = Array.BinarySearch(ordenados, 12);
Console.WriteLine(indice >= 0 ? indice : -1);
