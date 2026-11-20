// CodeTrace | Referencia: C# 12 y .NET SDK 8.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Compilar/verificar: dotnet new console -f net8.0 -o "$env:TEMP\codetrace-csharp" --force; Copy-Item '.\01-fundamentos/sintaxis\Tipos.cs' "$env:TEMP\codetrace-csharp\Program.cs" -Force; dotnet build "$env:TEMP\codetrace-csharp"
// Ejecutar: dotnet run --project "$env:TEMP\codetrace-csharp"

using System;

int cantidad = 3;
decimal precio = 12.5m;
decimal total = cantidad * precio;
Console.WriteLine(total);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\01-fundamentos\sintaxis\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   37.5
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
