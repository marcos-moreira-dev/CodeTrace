// CodeTrace | Referencia: C# 12 / .NET 8
// Compilar en COPIA desde esta unidad: dotnet new console -f net8.0 -o "$env:TEMP\ct-cs-curso" --force; Remove-Item "$env:TEMP\ct-cs-curso\Program.cs"; Copy-Item *.cs "$env:TEMP\ct-cs-curso"; dotnet build "$env:TEMP\ct-cs-curso"
// Ejecutar: dotnet run --project "$env:TEMP\ct-cs-curso"

using System;
namespace CodeTrace.Practica.HashAbierto;

/// <summary>
/// Cliente hash abierto: colisión, actualización y saturación
/// contra hash por encadenamiento. No se borra; sin tombstones,
/// eliminar una celda rompería una cadena de sondeo.
/// </summary>
public static class MainHashAbierto
{
    public static void Main()
    {
        var hash=new HashAbierto(2);
        hash.Poner("A",1);hash.Poner("B",2);hash.Poner("A",3);
        if(hash.Buscar("A")!=3||hash.Buscar("B")!=2||
           hash.Buscar("Z")!=null||hash.Cantidad!=2)throw new Exception("Hash");
        try{hash.Poner("C",4);throw new Exception("Se aceptó tabla llena");}
        catch(InvalidOperationException){Console.WriteLine("Tabla llena controlada");}
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $d=Join-Path $env:TEMP "ct-ejemplo-csharp"
 *   if (Test-Path $d) { Remove-Item $d -Recurse -Force }
 *   dotnet new console -f net8.0 -o $d --force
 *   Remove-Item "$d\Program.cs" -ErrorAction SilentlyContinue
 *   Copy-Item .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\*.cs $d -Force
 *   dotnet run --project $d
 * Salida esperada:
 *   Tabla llena controlada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
