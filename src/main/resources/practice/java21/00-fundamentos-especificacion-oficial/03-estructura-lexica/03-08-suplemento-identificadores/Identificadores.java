// Tema:
// Un identificador distingue variables y declaraciones. Java admite letras Unicode y
// distingue mayúsculas de minúsculas. No se pueden usar palabras reservadas como
// identificadores.
// Compilar: javac --release 21 -encoding UTF-8 -d . Identificadores.java
// Ejecutar: java Identificadores
// Comandos desde la carpeta de la unidad.

public class Identificadores {
    public static void main(String[] args) {
        int tamaño = 8;
        int cantidad = 3;
        int Cantidad = 5;
        System.out.println(tamaño);
        System.out.println(cantidad + " " + Cantidad);
    }
}

// Salida esperada:
// 8
// 3 5
