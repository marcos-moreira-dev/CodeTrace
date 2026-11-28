// Tema:
// Atomicidad didáctica: preparar cambios en una copia privada, validar fondos y publicar ambos saldos juntos al confirmar. Rollback descarta el borrador. Sin concurrencia, disco ni garantías ACID reales.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 Cuenta.java TransaccionSimulada.java MainTransaccion.java
// Ejecutar: java MainTransaccion

public record Cuenta(String nombre, int saldoCentavos) {
    public Cuenta {
        if (saldoCentavos < 0) throw new IllegalArgumentException("Saldo");
    }
}

// Salida esperada:
// 700 800
// Rollback
// 700 800
