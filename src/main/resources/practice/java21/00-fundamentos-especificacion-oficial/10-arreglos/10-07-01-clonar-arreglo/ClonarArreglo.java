// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §10.7; ejemplo 10.7-1
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
