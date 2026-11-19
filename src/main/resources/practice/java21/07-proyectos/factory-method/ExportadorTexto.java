// CodeTrace | Referencia: Java 21, proyecto Factory Method
// Compilar: javac --release 21 -d out 07-proyectos/factory-method/*.java
package codetrace.factorymethod;
public final class ExportadorTexto implements Exportador {
    @Override public String exportar(String contenido) {
        return "TEXTO: " + contenido;
    }
}
