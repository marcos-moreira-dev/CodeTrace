// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.9; ejemplo 8.4.9-1
// Compilar: javac --release 21 -encoding UTF-8 -d . SobrecargaMetodos.java
// Ejecutar: java SobrecargaMetodos
// Comandos desde la carpeta de la unidad.

public class SobrecargaMetodos {
    static void imprimir(int n) { System.out.println("int " + n); }
    static void imprimir(double n) { System.out.println("double " + n); }
    public static void main(String[] args) {
        imprimir(4);
        imprimir(4.5);
    }
}

// Salida esperada:
// int 4
// double 4.5
