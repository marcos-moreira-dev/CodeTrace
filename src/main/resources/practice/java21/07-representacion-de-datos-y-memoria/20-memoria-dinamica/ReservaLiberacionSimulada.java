// Tema:
// Un arreglo representa unidades de memoria: cero indica libre y un identificador indica
// ocupado. Reservar y liberar aquí solo cambia el modelo, no llama a malloc/free.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 ReservaLiberacionSimulada.java
// Ejecutar: java ReservaLiberacionSimulada

public class ReservaLiberacionSimulada {
    public static void main(String[] args) {
        int[] memoria = new int[6];
        for (int i = 1; i < 4; i++) memoria[i] = 1;
        System.out.println(java.util.Arrays.toString(memoria));
        for (int i = 0; i < memoria.length; i++) {
            if (memoria[i] == 1) memoria[i] = 0;
        }
        System.out.println(java.util.Arrays.toString(memoria));
    }
}

// Salida esperada:
// [0, 1, 1, 1, 0, 0]
// [0, 0, 0, 0, 0, 0]
