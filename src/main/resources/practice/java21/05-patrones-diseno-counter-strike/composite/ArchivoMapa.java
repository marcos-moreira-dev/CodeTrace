// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.composite.MainCompositeCS
// Dominio: Counter-Strike — árbol de mapas y archivos del juego, sin cargar contenidos protegidos.
// Patrón: Composite; ejercicio educativo no oficial.
// Glosario: Mapa = escenario virtual; recurso = archivo del videojuego; carpeta = contenedor de recursos.
package codetrace.practica.counterstrike.composite;

/** Hoja del árbol: un archivo representa un único recurso de mapa. */
public record ArchivoMapa(String nombre) implements RecursoMapa {
    @Override public int contar() { return 1; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.composite.MainCompositeCS
 * Nota: ArchivoMapa.java pertenece al ejercicio; el cliente es MainCompositeCS.java.
 * Salida esperada:
 *   Mapas registrados: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
