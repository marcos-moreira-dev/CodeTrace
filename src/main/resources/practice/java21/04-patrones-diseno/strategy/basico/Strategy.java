// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en copia: javac --release 21 -encoding UTF-8 Strategy.java MainStrategy.java
// Ejecutar: java MainStrategy

/**
 * Contexto del patrón Strategy: delega una regla de negocio.
 *
 * Un @FunctionalInterface con un único método abstracto admite
 * expresiones lambda y referencias a métodos de Java 21.
 */
public final class Strategy {
    @FunctionalInterface
    public interface Descuento { double aplicar(double total); }

    public static double cobrar(double total, Descuento estrategia) {
        if (total < 0) throw new IllegalArgumentException("Subtotal");
        return estrategia.aplicar(total);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainStrategy
 * Nota: Strategy.java es un componente; la salida corresponde al cliente MainStrategy.java.
 * Salida esperada:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
