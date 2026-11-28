// Tema:
// Los índices válidos de un arreglo van desde cero hasta length-1. El acceso fuera de ese
// intervalo provoca ArrayIndexOutOfBoundsException.
// Compilar: javac --release 21 -encoding UTF-8 -d . AccesoArreglo.java
// Ejecutar: java AccesoArreglo
// Comandos desde la carpeta de la unidad.

public class AccesoArreglo {
    public static void main(String[] args) {
        int[] valores = {4, 8};
        System.out.println(valores[1]);
        System.out.println(valores[2]);
    }
}

// Salida esperada:
// 8
// Después: ArrayIndexOutOfBoundsException (intencional).
