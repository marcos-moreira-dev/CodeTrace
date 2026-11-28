// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.7.1; ejemplo 15.7.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . OrdenIzquierda.java
// Ejecutar: java OrdenIzquierda
// Comandos desde la carpeta de la unidad.

public class OrdenIzquierda {
    public static void main(String[] args) {
        int n = 3;
        int resultado = n + (n = 8);
        System.out.println(resultado);
        System.out.println(n);
    }
}

// Salida esperada:
// 11
// 8
