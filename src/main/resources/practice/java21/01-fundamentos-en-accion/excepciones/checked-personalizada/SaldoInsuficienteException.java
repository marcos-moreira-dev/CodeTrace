// Compilar (desde esta carpeta): javac -encoding UTF-8 Cuenta.java MainExcepcionChecked.java SaldoInsuficienteException.java
// Ejecutar: java MainExcepcionChecked

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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Cuenta.java MainExcepcionChecked.java SaldoInsuficienteException.java
 *   java MainExcepcionChecked
 * Nota: SaldoInsuficienteException.java es un componente; la salida corresponde al cliente MainExcepcionChecked.java.
 * Salida esperada:
 *   No se retiraron 200; hay 70
 *   Saldo preservado: 70
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
