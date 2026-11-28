// Tema:
// Clonar un arreglo multidimensional realiza una copia superficial. El arreglo exterior cambia
// de identidad, pero los subarreglos referenciados pueden seguir compartidos.
// Compilar: javac --release 21 -encoding UTF-8 -d . ClonarMatriz.java
// Ejecutar: java ClonarMatriz
// Comandos desde la carpeta de la unidad.

public class ClonarMatriz {
    public static void main(String[] args) {
        int[][] a = {{1}, {2}};
        int[][] b = a.clone();
        b[0][0] = 9;
        System.out.println(a == b);
        System.out.println(a[0] == b[0]);
        System.out.println(a[0][0]);
    }
}

// Salida esperada:
// false
// true
// 9
