// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.tarifas.MainTarifas
package codetrace.practica.tarifas;

/**
 * Cliente del patrón Strategy usando una interfaz funcional.
 *
 * <p>Un sistema de cobros tiene reglas intercambiables de cálculo. En vez
 * de multiplicar condicionales dentro del cliente, se selecciona una
 * estrategia que cumple el contrato. Java permite implementar ese contrato
 * tanto con clases tradicionales como con lambdas.
 *
 * <p>Roles: EstrategiaTarifa = contrato; Tarifas = proveedores concretos;
 * MainTarifas = cliente que elige el comportamiento. No hay MVC porque no
 * existe una interfaz visual o controlador independiente que lo justifique.
 */
public final class MainTarifas {
    static double cobrar(double subtotal, EstrategiaTarifa estrategia) {
        return estrategia.total(subtotal);
    }

    public static void main(String[] args) {
        System.out.println(cobrar(100.0, Tarifas.normal()));
        System.out.println(cobrar(100.0, Tarifas.descuento(0.15)));
    }
}
