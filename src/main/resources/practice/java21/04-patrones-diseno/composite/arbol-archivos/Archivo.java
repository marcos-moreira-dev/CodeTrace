// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Árbol de archivos de mapas y recursos del videojuego.
// Patrón composite: Tratar hojas y contenedores con una misma interfaz.
// Glosario: Mapa: escenario virtual; recurso: archivo usado por el juego.
// Este archivo define exactamente un tipo reutilizable, sin main.
// Compilar: desde la carpeta java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

package codetrace.practica.patrones.composite;

/** Ejemplo de consola: ejecutar codetrace.practica.patrones.composite.MainComposite; salida esperada: Bytes: 220. */

/** Hoja inmutable; su peso se obtiene del componente del record. */
public record Archivo(String nombre, int bytes) implements Elemento {
    public Archivo {
        if (bytes < 0) throw new IllegalArgumentException("bytes");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.composite.MainComposite
 * Nota: Archivo.java es un componente; la salida corresponde al cliente MainComposite.java.
 * Salida esperada:
 *   Bytes: 220
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
