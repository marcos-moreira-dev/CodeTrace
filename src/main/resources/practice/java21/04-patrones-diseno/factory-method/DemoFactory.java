// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.factorymethod.DemoFactory
package codetrace.factorymethod;
public final class DemoFactory {
    public static void main(String[] args) {
        CreadorExportadores creador = new CreadorTexto();
        System.out.println(creador.preparar("Factura 001"));
    }
}
