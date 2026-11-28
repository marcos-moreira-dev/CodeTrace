// Tema:
// El operador % calcula el resto de la división entera. El signo del resto no nulo coincide
// con el del dividendo, no necesariamente con el divisor.
// Compilar: javac --release 21 -encoding UTF-8 -d . RestoEntero.java
// Ejecutar: java RestoEntero
// Comandos desde la carpeta de la unidad.

public class RestoEntero {
    public static void main(String[] args) {
        System.out.println(7 % 3);
        System.out.println(-7 % 3);
        System.out.println(7 % -3);
        System.out.println(-7 % -3);
    }
}

// Salida esperada:
// 1
// -1
// 1
// -1
