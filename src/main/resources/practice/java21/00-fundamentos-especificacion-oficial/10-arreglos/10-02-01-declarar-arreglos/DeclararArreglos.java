// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §10.2; ejemplo 10.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . DeclararArreglos.java
// Ejecutar: java DeclararArreglos
// Comandos desde la carpeta de la unidad.

public class DeclararArreglos {
    public static void main(String[] args) {
        int[] enteros;
        short[][] matriz;
        enteros = new int[3];
        matriz = new short[2][4];
        String[] letras = {"a", "b"};
        System.out.println(enteros.length);
        System.out.println(matriz[0].length);
        System.out.println(letras[1]);
    }
}

// Salida esperada:
// 3
// 4
// b
