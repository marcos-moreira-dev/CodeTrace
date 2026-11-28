// Tema:
// Compara la selección de tres políticas ante la misma solicitud de dos unidades. Las
// direcciones son posiciones del modelo.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 Bloque.java FirstFit.java BestFit.java WorstFit.java MainAsignacion.java
// Ejecutar: java MainAsignacion

public class MainAsignacion {
    public static void main(String[] args) {
        Bloque[] libres = {new Bloque(0, 3), new Bloque(3, 2), new Bloque(6, 5)};
        System.out.println("First Fit: " + FirstFit.elegir(libres, 2));
        System.out.println("Best Fit: " + BestFit.elegir(libres, 2));
        System.out.println("Worst Fit: " + WorstFit.elegir(libres, 2));
    }
}

// Salida esperada:
// First Fit: 0
// Best Fit: 3
// Worst Fit: 6
