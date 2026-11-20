// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" MainExcepcionUnchecked

/**
 * Cliente: excepción propia que hereda de RuntimeException.
 *
 * <p>Checked y unchecked no indican automáticamente "grave" o "leve".
 * Lo fundamental es el CONTRATO entre el dominio y sus llamadores.
 * Esta unidad contrasta con checked-personalizada: aquí no hay obligación
 * de capturar la excepción en tiempo de compilación, pero un cliente
 * puede hacerlo para informar o devolver un estado apropiado.
 *
 * <p>Modelo: Inventario. Error: StockInsuficienteException.
 * El cliente observa que el despacho inválido no modifica existencias.
 */
public final class MainExcepcionUnchecked {
    public static void main(String[] args) {
        Inventario inventario = new Inventario("A-001", 5);
        inventario.despachar(2);
        try {
            inventario.despachar(10);
            throw new AssertionError("La operación debía fallar");
        } catch (StockInsuficienteException ex) {
            System.out.println("Rechazo controlado: " + ex.getMessage());
        }
        if (inventario.disponibles() != 3)
            throw new AssertionError("El stock fue modificado en error");
    }
}
