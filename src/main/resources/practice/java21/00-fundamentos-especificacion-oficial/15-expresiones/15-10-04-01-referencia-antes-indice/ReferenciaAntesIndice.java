// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.10.4; ejemplo 15.10.4-1
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
