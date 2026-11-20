// CodeTrace | Referencia: C# 12, .NET 8 SDK.
// Desde PowerShell en una COPIA del paquete:
// Preparar proyecto temporal: dotnet new console -f net8.0 -o "$env:TEMP\codetrace-csharp" --force
// Copiar ejemplo: Copy-Item '.\03-algoritmos\busqueda-binaria-basica\BusquedaBinaria.cs' "$env:TEMP\codetrace-csharp\Program.cs" -Force
// Compilar: dotnet build "$env:TEMP\codetrace-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\codetrace-csharp"

using System;
int[] ordenados = { 2, 4, 8, 12, 16 };
int indice = Array.BinarySearch(ordenados, 12);
Console.WriteLine(indice >= 0 ? indice : -1);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\03-algoritmos\busqueda-binaria-basica\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
