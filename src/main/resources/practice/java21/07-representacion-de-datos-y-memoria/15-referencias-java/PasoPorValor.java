// Tema:
// Java copia el valor de cada argumento. Cambiar una variable de parámetro no cambia la
// variable del llamador; modificar el objeto compartido sí puede observarse.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 PasoPorValor.java
// Ejecutar: java PasoPorValor

public class PasoPorValor {
static void cambiar(int valor, int[] datos) {
        valor = 9;
        datos[0] = 9;
        datos = new int[]{100};
    }

    public static void main(String[] args) {
        int n = 3;
        int[] datos = {3};
        cambiar(n, datos);
        System.out.println(n);
        System.out.println(datos[0]);
    }
}

// Salida esperada:
// 3
// 9
