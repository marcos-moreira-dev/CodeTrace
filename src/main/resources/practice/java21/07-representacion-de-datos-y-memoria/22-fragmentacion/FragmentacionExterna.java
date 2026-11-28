// Tema:
// La memoria libre puede estar dividida en regiones separadas. Aunque la suma libre
// alcance, una solicitud contigua puede no caber.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 FragmentacionExterna.java
// Ejecutar: java FragmentacionExterna

public class FragmentacionExterna {
    public static void main(String[] args) {
        int[] libres = {3, 2, 4};
        int total = 0, mayor = 0;
        for (int bloque : libres) {
            total += bloque;
            if (bloque > mayor) mayor = bloque;
        }
        System.out.println(total);
        System.out.println(mayor >= 6);
    }
}

// Salida esperada:
// 9
// false
