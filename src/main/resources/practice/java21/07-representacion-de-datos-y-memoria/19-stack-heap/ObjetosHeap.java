// Tema:
// En el modelo de la JVM los objetos y arreglos pertenecen al heap. Dos instancias
// distintas pueden contener los mismos valores; la JVM puede optimizar su ubicación real.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 ObjetosHeap.java
// Ejecutar: java ObjetosHeap

public class ObjetosHeap {
    public static void main(String[] args) {
        int[] a = new int[]{5};
        int[] b = new int[]{5};
        System.out.println(a == b);
        System.out.println(a[0] == b[0]);
    }
}

// Salida esperada:
// false
// true
