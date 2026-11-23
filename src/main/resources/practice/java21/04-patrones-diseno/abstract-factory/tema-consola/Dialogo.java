// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Un archivo por contrato o implementación del patrón Abstract Factory.
// Compilar desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" codetrace.practica.patrones.abstractfactory.MainAbstractFactory
// Salida esperada: [ OK ] / Ventana clara; < OK > / Ventana oscura.
package codetrace.practica.patrones.abstractfactory;

/** Producto abstracto Dialogo. */
@FunctionalInterface
public interface Dialogo {
    String pintar();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.abstractfactory.MainAbstractFactory
 * Nota: Dialogo.java es un componente; la salida corresponde al cliente MainAbstractFactory.java.
 * Salida esperada:
 *   [ OK ] / Ventana clara
 *   < OK > / Ventana oscura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
