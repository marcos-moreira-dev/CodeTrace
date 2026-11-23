// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reportes de un torneo competitivo ficticio.
// Patrón factory-method: Delegar la creación del tipo de exportador a un subtipo.
// Glosario: Reporte: resumen de resultados de rondas.
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.factorymethod.MainFactoryMethod
package codetrace.factorymethod;
/**
 * Cliente Factory Method: el creador abstracto define el flujo de
 * preparación y la subclase concreta decide el Exportador. No es
 * una función fábrica: el polimorfismo se aplica al creador.
 *
 * Archivos: CreadorExportadores, CreadorTexto, Exportador y
 * ExportadorTexto. El cliente depende de la abstracción creadora.
 */
public final class MainFactoryMethod {
    public static void main(String[] args) {
        CreadorExportadores creador = new CreadorTexto();
        System.out.println(creador.preparar("Factura 001"));
    }
}

/*
 * Salida esperada de consola:
 *   TEXTO: Factura 001
 * La subclase creadora escoge ExportadorTexto por Factory Method.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.factorymethod.MainFactoryMethod
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
