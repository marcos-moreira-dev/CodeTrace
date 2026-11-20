// CodeTrace | Referencia: Java 21 LTS (JDK Temurin 21).
// Desde PowerShell, en una COPIA de la carpeta java21:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\06-patrones\Factory.java'
// Sin main: es una clase o interfaz para compilar y reutilizar.

public final class Factory {
    interface Mensaje {
        String enviar();
    }

    record Correo(String destino) implements Mensaje {
        public String enviar() { return "Correo a " + destino; }
    }

    public static Mensaje crear(String destino) {
        return new Correo(destino);
    }
}
