// Tema:
// Un arreglo de referencias admite conversión a un arreglo de supertipo de sus componentes;
// un arreglo de primitivos es Object, pero no Object[].
// Compilar: javac --release 21 -encoding UTF-8 -d . SubtiposArreglo.java
// Ejecutar: java SubtiposArreglo
// Comandos desde la carpeta de la unidad.

public class SubtiposArreglo {
    public static void main(String[] args) {
        String[] palabras = {"sol"};
        Object[] objetos = palabras;
        Object primitivos = new int[2];
        System.out.println(objetos[0]);
        System.out.println(primitivos instanceof Object[]);
        System.out.println(primitivos instanceof Cloneable);
    }
}

// Salida esperada:
// sol
// false
// true
