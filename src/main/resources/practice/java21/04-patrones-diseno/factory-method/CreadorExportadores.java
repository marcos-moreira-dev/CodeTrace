// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Reportes de un torneo competitivo ficticio.
// Patrón factory-method: Delegar la creación del tipo de exportador a un subtipo.
// Glosario: Reporte: resumen de resultados de rondas.
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
package codetrace.factorymethod;
public abstract class CreadorExportadores {
    protected abstract Exportador crear();
    public final String preparar(String contenido) {
        return crear().exportar(contenido);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.factorymethod.MainFactoryMethod
 * Nota: CreadorExportadores.java es un componente; la salida corresponde al cliente MainFactoryMethod.java.
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
