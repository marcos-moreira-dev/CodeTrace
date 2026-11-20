// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar/Comprobar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-biblioteca" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: Consulte MainTarifas

package codetrace.practica.tarifas;

/** Contrato del cálculo, independiente de quien lo utilice. */
@FunctionalInterface
public interface EstrategiaTarifa {
    double total(double subtotal);
}
