// CodeTrace | Referencia: Java 21, proyecto Factory Method
// Compilar: javac --release 21 -d out 07-proyectos/factory-method/*.java
package codetrace.factorymethod;
public abstract class CreadorExportadores {
    protected abstract Exportador crear();
    public final String preparar(String contenido) {
        return crear().exportar(contenido);
    }
}
