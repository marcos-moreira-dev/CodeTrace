// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: usar desde la clase cliente Main... del mismo ejemplo

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
