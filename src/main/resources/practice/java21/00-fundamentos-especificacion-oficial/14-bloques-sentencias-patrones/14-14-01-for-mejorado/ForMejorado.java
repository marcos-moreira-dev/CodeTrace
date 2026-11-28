// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.14; ejemplo 14.14-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ForMejorado.java
// Ejecutar: java ForMejorado
// Comandos desde la carpeta de la unidad.

public class ForMejorado {
    public static void main(String[] args) {
        int[] a = {2, 4, 6};
        int total = 0;
        for (int x : a) total += x;
        System.out.println(total);
    }
}

// Salida esperada:
// 12
