// Tema:
// El for mejorado recorre los elementos de un arreglo o de un Iterable sin gestionar
// explícitamente un índice o iterador.
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
