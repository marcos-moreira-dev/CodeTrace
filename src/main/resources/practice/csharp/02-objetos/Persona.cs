public record Persona(string Nombre, int Edad) {
    public bool EsAdulto() => Edad >= 18;
}

public static class PruebaPersona {
    public static string Mostrar(Persona persona) {
        return persona.Nombre + ": " + persona.EsAdulto();
    }
}
