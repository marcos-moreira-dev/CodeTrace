// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: usar desde la clase cliente Main... del mismo ejemplo

/**
 * Excepción comprobada propia del dominio de una cuenta.
 *
 * Como extiende Exception y no RuntimeException, Java obliga a quien
 * llame a retirar() a capturarla o declararla en su firma con throws.
 * No emplear una checked exception para errores de programación.
 */
public final class SaldoInsuficienteException extends Exception {
    private final int solicitado;
    private final int disponible;

    public SaldoInsuficienteException(int solicitado, int disponible) {
        super("Solicitado=" + solicitado + ", disponible=" + disponible);
        this.solicitado = solicitado;
        this.disponible = disponible;
    }
    public int solicitado() { return solicitado; }
    public int disponible() { return disponible; }
}
