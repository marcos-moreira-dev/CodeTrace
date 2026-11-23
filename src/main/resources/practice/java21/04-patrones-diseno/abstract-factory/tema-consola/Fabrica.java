// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Un archivo por contrato o implementación del patrón Abstract Factory.
// Compilar desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\\ct-java21" codetrace.practica.patrones.abstractfactory.MainAbstractFactory
// Salida esperada: [ OK ] / Ventana clara; < OK > / Ventana oscura.
package codetrace.practica.patrones.abstractfactory;

/** Contrato que crea la familia de productos relacionada. */
public interface Fabrica {
    Boton boton();
    Dialogo dialogo();
}
