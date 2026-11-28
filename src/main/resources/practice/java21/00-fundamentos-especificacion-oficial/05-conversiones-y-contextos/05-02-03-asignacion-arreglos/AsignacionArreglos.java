// Tema:
// Los arreglos de referencia son covariantes: String[] es subtipo de Object[]. El arreglo
// conserva su tipo real y la JVM comprueba las escrituras.
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionArreglos.java
// Ejecutar: java AsignacionArreglos
// Comandos desde la carpeta de la unidad.

public class AsignacionArreglos {
    public static void main(String[] args) {
        String[] palabras = {"uno", "dos"};
        Object[] objetos = palabras;
        Object arreglo = palabras;
        System.out.println(objetos[0]);
        System.out.println(arreglo == objetos);
    }
}

// Salida esperada:
// uno
// true
