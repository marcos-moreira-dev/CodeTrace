// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §10.4; ejemplo 10.4-1
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
