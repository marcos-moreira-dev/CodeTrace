// Tema:
// Modelo redo-only con checkpoint y log en memoria: cada cambio se registra antes de publicarse, y un marcador commit confirma la transacción. Tras una caída simulada se parte del checkpoint y se rehacen solo cambios confirmados. No demuestra fsync, durabilidad física ni recuperación general de un motor.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 EntradaLog.java AlmacenSimulado.java MainRecuperacion.java
// Ejecutar: java MainRecuperacion

public record EntradaLog(int transaccion, String clave, int valor, boolean commit) {
    public static EntradaLog cambio(int tx, String clave, int valor) {
        return new EntradaLog(tx, clave, valor, false);
    }
    public static EntradaLog confirmar(int tx) {
        return new EntradaLog(tx, "", 0, true);
    }
}

// Salida esperada:
// Pan: 15
// Leche: 8
