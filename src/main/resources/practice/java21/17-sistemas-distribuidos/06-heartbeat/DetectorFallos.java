// Tema:
// Un detector por heartbeat sospecha de un nodo si transcurre demasiado tiempo sin recibir noticias. Una demora de red puede producir una sospecha falsa. Tiempo discreto y controlado, no monitor de red real.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Nodo.java DetectorFallos.java MainHeartbeat.java
// Ejecutar: java MainHeartbeat

import java.util.HashMap;

public class DetectorFallos {
    private final int plazo;
    private final HashMap<String,Integer> ultimo = new HashMap<>();
    public DetectorFallos(int plazo) {
        if (plazo <= 0) throw new IllegalArgumentException("Plazo");
        this.plazo = plazo;
    }
    public void recibir(String id, int instante) { ultimo.merge(id, instante, Math::max); }
    public boolean sospechoso(String id, int ahora) {
        Integer anterior = ultimo.get(id);
        return anterior == null || ahora - anterior >= plazo;
    }
}

// Salida esperada:
// t=4: false
// t=5: true
// t=6: false
