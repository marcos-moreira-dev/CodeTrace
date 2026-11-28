// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.26.2; ejemplo 15.26.2-2
// Compilar: javac --release 21 -encoding UTF-8 -d . GuardarValorIzquierdo.java
// Ejecutar: java GuardarValorIzquierdo
// Comandos desde la carpeta de la unidad.

public class GuardarValorIzquierdo {
    public static void main(String[] args) {
        int n = 2;
        int[] a = {2};
        n += (n = 3) * (n + 1);
        a[0] += (a[0] = 3) * (a[0] + 1);
        System.out.println(n);
        System.out.println(a[0]);
    }
}

// Salida esperada:
// 14
// 14
