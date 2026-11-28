// Tema:
// La mediana es el valor central de una muestra ordenada. Si hay dos valores
// centrales se promedian. Ordenar no es el objeto de este ejercicio.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 Mediana.java
// Ejecutar: java Mediana

public class Mediana {
    public static void main(String[] args) {
        int[] datos = {2, 4, 8, 10};
        int centro = datos.length / 2;
        double mediana;
        if (datos.length % 2 == 0) {
            mediana = ((double) datos[centro - 1] + datos[centro]) / 2;
        } else {
            mediana = datos[centro];
        }
        System.out.println(mediana);
    }
}

// Salida esperada:
// 6.0
