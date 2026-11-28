// Compilar (desde esta carpeta): javac -encoding UTF-8 Cuenta.java MainExcepcionChecked.java SaldoInsuficienteException.java
// Ejecutar: java MainExcepcionChecked

/** Modelo que protege el invariante: el saldo nunca es negativo. */
public final class Cuenta {
    private int saldo;
    public Cuenta(int saldoInicial) {
        if (saldoInicial < 0) throw new IllegalArgumentException("Saldo inicial");
        saldo = saldoInicial;
    }

    /** @throws SaldoInsuficienteException rechazo recuperable de la operación */
    public void retirar(int monto) throws SaldoInsuficienteException {
        if (monto <= 0) throw new IllegalArgumentException("Monto positivo");
        if (monto > saldo) throw new SaldoInsuficienteException(monto, saldo);
        saldo -= monto;
    }
    public int saldo() { return saldo; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 Cuenta.java MainExcepcionChecked.java SaldoInsuficienteException.java
 *   java MainExcepcionChecked
 * Nota: Cuenta.java es un componente; la salida corresponde al cliente MainExcepcionChecked.java.
 * Salida esperada:
 *   No se retiraron 200; hay 70
 *   Saldo preservado: 70
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
