// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.17.3; ejemplo 15.17.3-1
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
