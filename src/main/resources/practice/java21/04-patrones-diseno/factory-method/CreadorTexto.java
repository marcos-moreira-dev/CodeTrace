// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
package codetrace.factorymethod;
public final class CreadorTexto extends CreadorExportadores {
    @Override protected Exportador crear() {
        return new ExportadorTexto();
    }
}
