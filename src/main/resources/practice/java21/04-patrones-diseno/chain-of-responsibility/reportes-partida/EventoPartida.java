// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practica-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practica-java" codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
package codetrace.practica.patrones.cadena;

import java.util.Objects;

/**
 * Solicitud inmutable que viaja por la cadena de responsables.
 * Cada evento contiene solo el tipo y una descripción concreta.
 */
public record EventoPartida(TipoEvento tipo, String detalle) {
    public EventoPartida {
        Objects.requireNonNull(tipo, "tipo");
        Objects.requireNonNull(detalle, "detalle");
        if (detalle.isBlank()) throw new IllegalArgumentException("Detalle vacío");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.cadena.MainCadenaResponsabilidad
 * Nota: EventoPartida.java forma parte del mismo ejercicio; ejecutar MainCadenaResponsabilidad.java.
 * Salida esperada:
 *   Objetivo: asegurar el sitio B
 *   Reconocimiento: enemigo en pasillo central
 *   Apoyo: solicitar munición adicional
 *   Sin responsable: señal desconocida
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
