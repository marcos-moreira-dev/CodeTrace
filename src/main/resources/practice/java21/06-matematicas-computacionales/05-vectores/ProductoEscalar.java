// Tema:
// El producto escalar suma los productos de componentes correspondientes.
// Los vectores deben tener la misma dimensión.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 ProductoEscalar.java
// Ejecutar: java ProductoEscalar

public class ProductoEscalar {
    public static void main(String[] args) {
        int[] a = {1, 2, 3}, b = {4, 5, 6};
        int producto = 0;
        for (int i = 0; i < a.length; i++) producto += a[i] * b[i];
        System.out.println(producto);
    }
}

// Salida esperada:
// 32
