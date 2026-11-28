// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.3; ejemplo 8.1.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . InternaEstatica.java
// Ejecutar: java InternaEstatica
// Comandos desde la carpeta de la unidad.

public class InternaEstatica {
    class Interior {
        static int numero = 8;
        static { System.out.println("inicializada"); }
        static int leer() { return numero; }
        static class Anidada {}
    }
    public static void main(String[] args) {
        System.out.println(Interior.leer());
    }
}

// Salida esperada:
// inicializada
// 8
