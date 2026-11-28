// Tema:
// Un detector por heartbeat sospecha de un nodo si transcurre demasiado tiempo sin recibir noticias. Una demora de red puede producir una sospecha falsa. Tiempo discreto y controlado, no monitor de red real.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Nodo.java DetectorFallos.java MainHeartbeat.java
// Ejecutar: java MainHeartbeat

public class Nodo {
    private final String id;
    public Nodo(String id) { this.id = id; }
    public String id() { return id; }
    public void latido(DetectorFallos detector, int instante) { detector.recibir(id, instante); }
}

// Salida esperada:
// t=4: false
// t=5: true
// t=6: false
