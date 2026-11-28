// Tema:
// First Fit elige el primer bloque suficientemente grande; devuelve -1 si ninguno satisface
// la solicitud.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Bloque.java FirstFit.java BestFit.java WorstFit.java MainAsignacion.java
// Ejecutar: java MainAsignacion

class FirstFit {
    static int elegir(Bloque[] bloques, int solicitado) {
        for (Bloque b : bloques) {
            if (b.tamano >= solicitado) return b.inicio;
        }
        return -1;
    }
}

// Salida esperada:
// First Fit: 0
// Best Fit: 3
// Worst Fit: 6
