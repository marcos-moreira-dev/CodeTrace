// Tema:
// Cada participante conserva un vector de contadores. Al recibir combina máximos componente a componente e incrementa su posición. Dos vectores incomparables representan eventos concurrentes en este modelo.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 RelojVectorial.java MainCausalidad.java
// Ejecutar: java MainCausalidad

import java.util.Arrays;

public class MainCausalidad {
    public static void main(String[] args) {
        RelojVectorial a = new RelojVectorial(0, 2), b = new RelojVectorial(1, 2);
        int[] x = a.evento(), y = b.evento();
        System.out.println("A: " + Arrays.toString(x));
        System.out.println("B: " + Arrays.toString(y));
        System.out.println("Concurrentes: " + RelojVectorial.concurrentes(x,y));
        int[] z = b.recibir(x);
        System.out.println("B recibe: " + Arrays.toString(z));
        System.out.println("A precede: " + RelojVectorial.precede(x,z));
    }
}

// Salida esperada:
// A: [1, 0]
// B: [0, 1]
// Concurrentes: true
// B recibe: [1, 2]
// A precede: true
