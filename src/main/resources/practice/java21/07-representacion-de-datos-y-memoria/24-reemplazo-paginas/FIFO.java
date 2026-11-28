// Tema:
// FIFO sustituye la página que entró primero. Un acierto no altera el orden de llegada. Las
// páginas del ejemplo son no negativas.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 FIFO.java LRU.java OPT.java MainReemplazo.java
// Ejecutar: java MainReemplazo

class FIFO {
    static int fallos(int[] referencias, int capacidad) {
        int[] marcos = new int[capacidad];
        java.util.Arrays.fill(marcos, -1);
        int siguiente = 0, fallos = 0;
        for (int pagina : referencias) {
            boolean presente = false;
            for (int marco : marcos) if (marco == pagina) presente = true;
            if (!presente) {
                fallos++;
                marcos[siguiente] = pagina;
                siguiente = (siguiente + 1) % capacidad;
            }
        }
        return fallos;
    }
}

// Salida esperada:
// FIFO: 10
// LRU: 9
// OPT: 7
