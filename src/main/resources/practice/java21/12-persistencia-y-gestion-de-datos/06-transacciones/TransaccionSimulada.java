// Tema:
// Atomicidad didáctica: preparar cambios en una copia privada, validar fondos y publicar ambos saldos juntos al confirmar. Rollback descarta el borrador. Sin concurrencia, disco ni garantías ACID reales.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 Cuenta.java TransaccionSimulada.java MainTransaccion.java
// Ejecutar: java MainTransaccion

public class TransaccionSimulada {
    private Cuenta[] estado;
    private Cuenta[] borrador;

    public TransaccionSimulada(Cuenta a, Cuenta b) { estado = new Cuenta[]{a, b}; }
    public void comenzar() {
        if (borrador != null) throw new IllegalStateException("Transaccion activa");
        borrador = estado.clone();
    }
    public void transferir(int importe) {
        if (borrador == null) throw new IllegalStateException("Sin transaccion");
        Cuenta a = borrador[0], b = borrador[1];
        if (importe <= 0 || importe > a.saldoCentavos())
            throw new IllegalArgumentException("Fondos");
        int nuevoB = Math.addExact(b.saldoCentavos(), importe);
        borrador[0] = new Cuenta(a.nombre(), a.saldoCentavos() - importe);
        borrador[1] = new Cuenta(b.nombre(), nuevoB);
    }
    public void commit() {
        if (borrador == null) throw new IllegalStateException("Sin transaccion");
        estado = borrador;
        borrador = null;
    }
    public void rollback() { borrador = null; }
    public Cuenta[] estado() { return estado.clone(); }
}

// Salida esperada:
// 700 800
// Rollback
// 700 800
