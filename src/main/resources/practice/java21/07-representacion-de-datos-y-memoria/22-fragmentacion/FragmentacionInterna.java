// Tema:
// Cuando se asigna una unidad fija mayor que la solicitud, parte del espacio reservado
// queda sin utilizar dentro del bloque.
// Modalidad: simulación conceptual; no administra memoria real.
// Compilar: javac --release 21 -encoding UTF-8 FragmentacionInterna.java
// Ejecutar: java FragmentacionInterna

public class FragmentacionInterna {
    public static void main(String[] args) {
        int unidad = 8, solicitado = 10;
        int bloques = (solicitado + unidad - 1) / unidad;
        System.out.println(bloques * unidad - solicitado);
    }
}

// Salida esperada:
// 6
