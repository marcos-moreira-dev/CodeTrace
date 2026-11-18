public record Persona(String nombre, int edad) {
    public Persona {
        if (edad < 0) {
            throw new IllegalArgumentException("Edad inválida");
        }
    }

    public boolean esMayorDeEdad() {
        return edad >= 18;
    }
}
