// Tema:
// Intercambiar dos parámetros locales no intercambia las variables del llamador. Para
// modificar dos elementos compartidos se cambian las posiciones del arreglo.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 IntercambioValores.java
// Ejecutar: java IntercambioValores

public class IntercambioValores {
static void intercambiar(int a, int b) {
        int aux = a;
        a = b;
        b = aux;
    }

    public static void main(String[] args) {
        int a = 2, b = 7;
        intercambiar(a, b);
        System.out.println(a + " " + b);
        int[] datos = {a, b};
        int aux = datos[0];
        datos[0] = datos[1];
        datos[1] = aux;
        System.out.println(datos[0] + " " + datos[1]);
    }
}

// Salida esperada:
// 2 7
// 7 2
