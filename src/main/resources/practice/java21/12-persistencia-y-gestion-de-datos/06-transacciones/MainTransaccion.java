// Tema:
// Atomicidad didáctica: preparar cambios en una copia privada, validar fondos y publicar ambos saldos juntos al confirmar. Rollback descarta el borrador. Sin concurrencia, disco ni garantías ACID reales.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 Cuenta.java TransaccionSimulada.java MainTransaccion.java
// Ejecutar: java MainTransaccion

public class MainTransaccion {
    static void mostrar(TransaccionSimulada tx) {
        Cuenta[] cuentas = tx.estado();
        System.out.println(cuentas[0].saldoCentavos() + " " + cuentas[1].saldoCentavos());
    }

    public static void main(String[] args) throws Exception {
        TransaccionSimulada tx = new TransaccionSimulada(new Cuenta("A", 1000), new Cuenta("B", 500));
        tx.comenzar();
        tx.transferir(300);
        tx.commit();
        mostrar(tx);
        tx.comenzar();
        try {
            tx.transferir(100);
            tx.transferir(900); // Falla después del primer cambio privado.
            tx.commit();
        } catch (IllegalArgumentException e) {
            tx.rollback();
            System.out.println("Rollback");
        }
        mostrar(tx);
    }
}

// Salida esperada:
// 700 800
// Rollback
// 700 800
