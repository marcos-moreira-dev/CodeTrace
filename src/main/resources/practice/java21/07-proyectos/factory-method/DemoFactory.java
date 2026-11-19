// CodeTrace | Referencia: Java 21, proyecto Factory Method
// Compilar: javac --release 21 -d out 07-proyectos/factory-method/*.java
package codetrace.factorymethod;
public final class DemoFactory {
    public static void main(String[] args) {
        CreadorExportadores creador = new CreadorTexto();
        System.out.println(creador.preparar("Factura 001"));
    }
}
