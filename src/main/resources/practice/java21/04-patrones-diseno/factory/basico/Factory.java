// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
// Sin main: es una clase o interfaz para compilar y reutilizar.

/**
 * Fábrica simple de mensajes, no patrón Factory Method por herencia.
 *
 * <p>Su única responsabilidad es concentrar el conocimiento de la clase
 * concreta Correo y devolverla mediante el contrato Mensaje. El cliente
 * depende del contrato, no de la decisión de creación. La versión GoF
 * con creadores polimórficos está en factory-method/.
 */
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainFactoryBasica
 * Nota: Factory.java es un componente; la salida corresponde al cliente MainFactoryBasica.java.
 * Salida esperada:
 *   Correo a cliente@example.invalid
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
