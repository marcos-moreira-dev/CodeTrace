// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §10.8; ejemplo 10.8-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ClaseArreglos.java
// Ejecutar: java ClaseArreglos
// Comandos desde la carpeta de la unidad.

public class ClaseArreglos {
    public static void main(String[] args) {
        int[] a = new int[1];
        int[] b = new int[3];
        System.out.println(a.getClass() == b.getClass());
        System.out.println(a.getClass().getComponentType());
    }
}

// Salida esperada:
// true
// int
