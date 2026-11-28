// Tema:
// Grafo de espera con recursos exclusivos de una instancia. Una arista i→j significa que i espera un recurso retenido por j. Un ciclo representa interbloqueo bajo estos supuestos; no se bloquea la JVM.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 EstadoBloqueos.java MainDeadlock.java
// Ejecutar: java MainDeadlock

public class MainDeadlock {
    public static void main(String[] args) throws Exception {
        System.out.println("Ciclo: " + new EstadoBloqueos(new int[]{1, 0}).ciclo());
        System.out.println("Ciclo: " + new EstadoBloqueos(new int[]{1, -1}).ciclo());
    }
}

// Salida esperada:
// Ciclo: true
// Ciclo: false
