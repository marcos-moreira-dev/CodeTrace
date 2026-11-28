// Tema:
// Dos referencias pueden identificar el mismo objeto; modificarlo a través de cualquiera
// afecta al mismo arreglo. Copiar la referencia no clona el objeto.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 AliasReferencias.java
// Ejecutar: java AliasReferencias

public class AliasReferencias {
    public static void main(String[] args) {
        int[] a = {4};
        int[] b = a;
        b[0] = 8;
        System.out.println(a[0]);
        System.out.println(a == b);
    }
}

// Salida esperada:
// 8
// true
