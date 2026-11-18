// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA del paquete: 
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\02-tipos\Persona.java'
// Sin punto de entrada main: esta clase se comprueba compilándola.

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
