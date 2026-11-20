// CodeTrace | Referencia: Java 21 LTS / JDK 21
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
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
