// CodeTrace | Referencia: Java 21, proyecto Factory Method
// Compilar: javac --release 21 -d out 07-proyectos/factory-method/*.java
package codetrace.factorymethod;
public final class CreadorTexto extends CreadorExportadores {
    @Override protected Exportador crear() {
        return new ExportadorTexto();
    }
}
