// CodeTrace | Referencia: C# 12 y .NET SDK 8.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Verificar/compilar: dotnet new console -f net8.0 -o "$env:TEMP\codetrace-csharp" --force; Copy-Item '.\01-fundamentos/objetos\Persona.cs' "$env:TEMP\codetrace-csharp\Persona.cs" -Force; dotnet build "$env:TEMP\codetrace-csharp"
// Clase sin punto de entrada; el comando verifica su compilación.

public record Persona(string Nombre, int Edad) {
    public bool EsAdulto() => Edad >= 18;
}

public static class PruebaPersona {
    public static string Mostrar(Persona persona) {
        return persona.Nombre + ": " + persona.EsAdulto();
    }
}
