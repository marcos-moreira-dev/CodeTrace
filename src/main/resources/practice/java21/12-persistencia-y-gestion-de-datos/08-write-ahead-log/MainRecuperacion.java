// Tema:
// Modelo redo-only con checkpoint y log en memoria: cada cambio se registra antes de publicarse, y un marcador commit confirma la transacción. Tras una caída simulada se parte del checkpoint y se rehacen solo cambios confirmados. No demuestra fsync, durabilidad física ni recuperación general de un motor.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 EntradaLog.java AlmacenSimulado.java MainRecuperacion.java
// Ejecutar: java MainRecuperacion

import java.util.Map;

public class MainRecuperacion {
    public static void main(String[] args) throws Exception {
        AlmacenSimulado almacen = new AlmacenSimulado(Map.of("pan", 10, "leche", 8));
        almacen.registrar(1, "pan", 15);
        almacen.commit(1);
        almacen.registrar(2, "leche", 99); // Sin commit.
        almacen.simularCaida();
        System.out.println("Pan: " + almacen.leer("pan"));
        System.out.println("Leche: " + almacen.leer("leche"));
    }
}

// Salida esperada:
// Pan: 15
// Leche: 8
