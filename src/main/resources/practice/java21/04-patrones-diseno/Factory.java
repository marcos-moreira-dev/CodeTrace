// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
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
