// Tema:
// clone() de un arreglo crea otro arreglo del mismo tipo y longitud. En un arreglo primitivo,
// los valores se copian y luego pueden modificarse de forma independiente.
// Compilar: javac --release 21 -encoding UTF-8 -d . ClonarArreglo.java
// Ejecutar: java ClonarArreglo
// Comandos desde la carpeta de la unidad.

public class ClonarArreglo {
    public static void main(String[] args) {
        int[] a = {1, 2};
        int[] b = a.clone();
        b[0] = 9;
        System.out.println(a == b);
        System.out.println(a[0] + " " + b[0]);
    }
}

// Salida esperada:
// false
// 1 9
