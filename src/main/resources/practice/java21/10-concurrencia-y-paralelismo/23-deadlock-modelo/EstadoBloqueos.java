// Tema:
// Grafo de espera con recursos exclusivos de una instancia. Una arista i→j significa que i espera un recurso retenido por j. Un ciclo representa interbloqueo bajo estos supuestos; no se bloquea la JVM.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 EstadoBloqueos.java MainDeadlock.java
// Ejecutar: java MainDeadlock

public class EstadoBloqueos {
    private final int[] esperaA;
    public EstadoBloqueos(int[] esperaA) { this.esperaA = esperaA.clone(); }
    public boolean ciclo() {
        for (int origen = 0; origen < esperaA.length; origen++) {
            boolean[] visitado = new boolean[esperaA.length];
            int actual = origen;
            while (actual != -1) {
                if (visitado[actual]) return true;
                visitado[actual] = true;
                actual = esperaA[actual];
            }
        }
        return false;
    }
}

// Salida esperada:
// Ciclo: true
// Ciclo: false
