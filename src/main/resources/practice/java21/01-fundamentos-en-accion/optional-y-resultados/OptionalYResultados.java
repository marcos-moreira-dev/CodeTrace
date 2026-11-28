// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" OptionalYResultados

import java.util.Map;
import java.util.Optional;

/**
 * Cliente de Optional: expresar explícitamente una búsqueda sin resultado.
 *
 * <p>Un método que devuelve Optional<String> obliga al llamador a tratar
 * la ausencia. map transforma únicamente el valor presente y orElseGet
 * calcula una alternativa si hace falta. Optional no sustituye a todas
 * las referencias ni debe introducirse mecánicamente en cada campo.
 */
public class OptionalYResultados {
    static Optional<String> encontrar(Map<String, String> personas, String id) {
        return Optional.ofNullable(personas.get(id));
    }

    public static void main(String[] args) {
        Map<String, String> nombres = Map.of("42", "Ada", "13", "Grace");
        System.out.println(encontrar(nombres, "42")
            .map(String::toUpperCase)
            .orElseGet(() -> "SIN DATOS"));
        System.out.println(encontrar(nombres, "99").orElse("NO EXISTE"));
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out OptionalYResultados
 * Salida esperada:
 *   ADA
 *   NO EXISTE
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
