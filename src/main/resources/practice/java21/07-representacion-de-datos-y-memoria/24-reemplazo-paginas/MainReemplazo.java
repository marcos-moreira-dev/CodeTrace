// Tema:
// Los tres modelos reciben la misma secuencia y tres marcos inicialmente vacíos. Se compara
// el número de accesos que requieren cargar una página.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 FIFO.java LRU.java OPT.java MainReemplazo.java
// Ejecutar: java MainReemplazo

public class MainReemplazo {
    public static void main(String[] args) {
        int[] referencias = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2};
        System.out.println("FIFO: " + FIFO.fallos(referencias, 3));
        System.out.println("LRU: " + LRU.fallos(referencias, 3));
        System.out.println("OPT: " + OPT.fallos(referencias, 3));
    }
}

// Salida esperada:
// FIFO: 10
// LRU: 9
// OPT: 7
