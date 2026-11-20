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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainExcepcionChecked
 * Nota: Cuenta.java es un componente; la salida corresponde al cliente MainExcepcionChecked.java.
 * Salida esperada:
 *   No se retiraron 200; hay 70
 *   Saldo preservado: 70
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
