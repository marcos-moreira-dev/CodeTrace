// Tema:
// Modelo redo-only con checkpoint y log en memoria: cada cambio se registra antes de publicarse, y un marcador commit confirma la transacción. Tras una caída simulada se parte del checkpoint y se rehacen solo cambios confirmados. No demuestra fsync, durabilidad física ni recuperación general de un motor.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 EntradaLog.java AlmacenSimulado.java MainRecuperacion.java
// Ejecutar: java MainRecuperacion

import java.util.*;

public class AlmacenSimulado {
    private final Map<String,Integer> checkpoint;
    private final List<EntradaLog> log = new ArrayList<>();
    private Map<String,Integer> datos;

    public AlmacenSimulado(Map<String,Integer> inicial) {
        checkpoint = new HashMap<>(inicial);
        datos = new HashMap<>(inicial);
    }
    public void registrar(int tx, String clave, int valor) {
        log.add(EntradaLog.cambio(tx, clave, valor));
    }
    public void commit(int tx) {
        log.add(EntradaLog.confirmar(tx));
        recuperar();
    }
    public void recuperar() {
        Set<Integer> confirmadas = new HashSet<>();
        for (EntradaLog e : log) if (e.commit()) confirmadas.add(e.transaccion());
        Map<String,Integer> nueva = new HashMap<>(checkpoint);
        for (EntradaLog e : log)
            if (!e.commit() && confirmadas.contains(e.transaccion()))
                nueva.put(e.clave(), e.valor());
        datos = nueva;
    }
    public void simularCaida() { datos = new HashMap<>(); recuperar(); }
    public int leer(String clave) { return datos.get(clave); }
}

// Salida esperada:
// Pan: 15
// Leche: 8
