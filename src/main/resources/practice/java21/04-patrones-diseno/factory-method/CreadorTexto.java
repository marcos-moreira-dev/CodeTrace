// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
package codetrace.factorymethod;
public final class CreadorTexto extends CreadorExportadores {
    @Override protected Exportador crear() {
        return new ExportadorTexto();
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
 * Nota: CreadorTexto.java es un componente; la salida corresponde al cliente MainFactoryMethod.java.
 * Salida esperada:
 *   TEXTO: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
