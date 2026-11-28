// Tema:
// La sobrecarga declara métodos del mismo nombre con diferentes listas de parámetros. El
// compilador elige la firma aplicable usando los tipos de los argumentos.
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
