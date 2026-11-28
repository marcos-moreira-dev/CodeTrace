// Tema:
// Un inicializador de arreglo enumera los valores iniciales de sus componentes. La cantidad de
// elementos determina su longitud y se admite una coma final.
// Compilar: javac --release 21 -encoding UTF-8 -d . InicializadorArreglo.java
// Ejecutar: java InicializadorArreglo
// Comandos desde la carpeta de la unidad.

public class InicializadorArreglo {
    public static void main(String[] args) {
        int[] datos = {7, 11, 13,};
        for (int dato : datos) System.out.println(dato);
    }
}

// Salida esperada:
// 7
// 11
// 13
