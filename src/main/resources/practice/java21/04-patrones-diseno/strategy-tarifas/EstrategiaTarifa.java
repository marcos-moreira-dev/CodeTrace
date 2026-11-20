// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
package codetrace.practica.tarifas;

/** Contrato del cálculo, independiente de quien lo utilice. */
@FunctionalInterface
public interface EstrategiaTarifa {
    double total(double subtotal);
}
