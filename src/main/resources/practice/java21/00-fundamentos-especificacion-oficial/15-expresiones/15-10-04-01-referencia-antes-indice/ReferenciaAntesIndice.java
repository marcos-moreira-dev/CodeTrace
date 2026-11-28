// Tema:
// En un acceso a arreglo se evalúa primero la referencia al arreglo y después el índice.
// Reasignar la variable durante el índice no modifica la referencia ya guardada.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaAntesIndice.java
// Ejecutar: java ReferenciaAntesIndice
// Comandos desde la carpeta de la unidad.

public class ReferenciaAntesIndice {
    public static void main(String[] args) {
        int[] a = {10, 20, 30};
        int[] b = {2, 0, 1};
        System.out.println(a[(a = b)[0]]);
        System.out.println(a[0]);
    }
}

// Salida esperada:
// 30
// 2
