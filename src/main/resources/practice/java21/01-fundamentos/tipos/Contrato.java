// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: ejecutar desde el cliente que utiliza este componente
// Sin punto de entrada main: esta clase se comprueba compilándola.

public interface Contrato {
    double calcular(double subtotal);

    default boolean aplica(double subtotal) {
        return subtotal > 0;
    }
}
