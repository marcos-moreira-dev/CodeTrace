// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-curso-java" MainExcepcionChecked

/**
 * Cliente y microlección: crear una excepción checked propia.
 *
 * <p>Una excepción no es exclusivamente un mensaje: puede transportar
 * datos de negocio, indicar el motivo preciso del rechazo y ofrecer una
 * decisión de recuperación. Aquí Cuenta protege su invariante y delega
 * al cliente el manejo del caso "no hay fondos".
 *
 * <p>Archivos: Cuenta.java (modelo), SaldoInsuficienteException.java
 * (tipo de fallo comprobado) y esta clase cliente. Conceptos:
 * {@code extends Exception}, {@code throws}, {@code throw}, {@code try/catch},
 * estado consistente antes y después del fallo.
 *
 * <p>El caso exitoso y el fallido se ejecutan sin entradas externas.
 * Un fallo en retirar no debe modificar el saldo anterior.
 */
public final class MainExcepcionChecked {
    public static void main(String[] args) {
        Cuenta cuenta = new Cuenta(100);
        try {
            cuenta.retirar(30);
            cuenta.retirar(200);
        } catch (SaldoInsuficienteException ex) {
            System.out.printf("No se retiraron %d; hay %d%n",
                    ex.solicitado(), ex.disponible());
        }
        if (cuenta.saldo() != 70) throw new AssertionError("Invariante roto");
        System.out.println("Saldo preservado: " + cuenta.saldo());
    }
}

/*
 * Salida esperada de consola (en este orden):
 *   No se retiraron 200; hay 70
 *   Saldo preservado: 70
 * La operación rechazada no altera el saldo; el catch recupera el flujo.
 */
