// Tema:
// Worst Fit escoge el mayor bloque disponible que satisface el tamaño solicitado.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Bloque.java FirstFit.java BestFit.java WorstFit.java MainAsignacion.java
// Ejecutar: java MainAsignacion

class WorstFit {
    static int elegir(Bloque[] bloques, int solicitado) {
        Bloque mayor = null;
        for (Bloque b : bloques) {
            if (b.tamano >= solicitado && (mayor == null || b.tamano > mayor.tamano)) {
                mayor = b;
            }
        }
        return mayor == null ? -1 : mayor.inicio;
    }
}

// Salida esperada:
// First Fit: 0
// Best Fit: 3
// Worst Fit: 6
