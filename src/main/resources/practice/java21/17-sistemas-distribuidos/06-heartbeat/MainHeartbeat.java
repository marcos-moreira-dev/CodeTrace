// Tema:
// Un detector por heartbeat sospecha de un nodo si transcurre demasiado tiempo sin recibir noticias. Una demora de red puede producir una sospecha falsa. Tiempo discreto y controlado, no monitor de red real.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Nodo.java DetectorFallos.java MainHeartbeat.java
// Ejecutar: java MainHeartbeat

public class MainHeartbeat {
    public static void main(String[] args) {
        Nodo nodo = new Nodo("A");
        DetectorFallos detector = new DetectorFallos(5);
        nodo.latido(detector, 0);
        System.out.println("t=4: " + detector.sospechoso("A", 4));
        System.out.println("t=5: " + detector.sospechoso("A", 5));
        nodo.latido(detector, 6);
        System.out.println("t=6: " + detector.sospechoso("A", 6));
    }
}

// Salida esperada:
// t=4: false
// t=5: true
// t=6: false
