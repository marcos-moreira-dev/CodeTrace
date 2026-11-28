// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.6.1; ejemplo 6.5.6.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . NombreConstante.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class NombreConstante {
    static int contador;
    static final int LIMITE = 5;
    public static void main(String[] args) {
        int local = 1;
        contador = 2;
        LIMITE = 8;
        System.out.println(local + contador + LIMITE);
    }
}

// Salida esperada:
// Error: cannot assign a value to final variable LIMITE
