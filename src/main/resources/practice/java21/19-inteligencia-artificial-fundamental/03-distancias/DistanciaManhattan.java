// Tema:
// La distancia Manhattan suma diferencias absolutas por coordenada. Se convierte a long antes de restar para evitar desbordamiento de int.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 DistanciaManhattan.java
// Ejecutar: java DistanciaManhattan

public class DistanciaManhattan {
    static long distancia(int[] a, int[] b) {
        if (a.length != b.length) throw new IllegalArgumentException("Dimension");
        long suma = 0;
        for (int i = 0; i < a.length; i++) suma += Math.abs((long) a[i] - b[i]);
        return suma;
    }

    public static void main(String[] args) {
        System.out.println("Distancia: " + distancia(new int[]{2, 3}, new int[]{5, 7}));
        System.out.println("Iguales: " + distancia(new int[]{1, 2}, new int[]{1, 2}));
    }
}

// Salida esperada:
// Distancia: 7
// Iguales: 0
