// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.18.1; ejemplo 15.18.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Concatenacion.java
// Ejecutar: java Concatenacion
// Comandos desde la carpeta de la unidad.

public class Concatenacion {
    public static void main(String[] args) {
        System.out.println(2 + 3 + "x");
        System.out.println("x" + 2 + 3);
    }
}

// Salida esperada:
// 5x
// x23
