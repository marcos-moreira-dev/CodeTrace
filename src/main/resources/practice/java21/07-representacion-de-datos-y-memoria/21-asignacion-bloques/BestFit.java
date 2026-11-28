// Tema:
// Best Fit elige el menor bloque capaz de satisfacer la solicitud. Minimiza el sobrante
// inmediato, no toda la fragmentación futura.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Bloque.java FirstFit.java BestFit.java WorstFit.java MainAsignacion.java
// Ejecutar: java MainAsignacion

class BestFit {
    static int elegir(Bloque[] bloques, int solicitado) {
        Bloque mejor = null;
        for (Bloque b : bloques) {
            if (b.tamano >= solicitado && (mejor == null || b.tamano < mejor.tamano)) {
                mejor = b;
            }
        }
        return mejor == null ? -1 : mejor.inicio;
    }
}

// Salida esperada:
// First Fit: 0
// Best Fit: 3
// Worst Fit: 6
