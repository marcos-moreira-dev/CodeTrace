// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: desde el cliente Main... de la misma carpeta

package codetrace.practica.patrones.abstractfactory;

/**
 * Abstract Factory crea PRODUCTOS RELACIONADOS coherentes entre sí.
 * No se confunde con Factory Method: aquí una fábrica produce una
 * FAMILIA, no un solo producto de una subclase creadora.
 */
public final class FamiliaInterfaz {
    private FamiliaInterfaz() {}
    public interface Boton { String pintar(); }
    public interface Dialogo { String pintar(); }
    public interface Fabrica { Boton boton(); Dialogo dialogo(); }
    public static final class Claro implements Fabrica {
        public Boton boton() { return ()->"[ OK ]"; }
        public Dialogo dialogo() { return ()->"Ventana clara"; }
    }
    public static final class Oscuro implements Fabrica {
        public Boton boton() { return ()->"< OK >"; }
        public Dialogo dialogo() { return ()->"Ventana oscura"; }
    }
}
