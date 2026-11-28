// Tema:
// La clase de un arreglo depende de su tipo de componente y número de dimensiones, no de su
// longitud. getComponentType permite consultar el tipo de componente.
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
