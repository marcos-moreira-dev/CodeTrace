// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.4; ejemplo 6.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . SombreadoInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class SombreadoInvalido {
    public static void main(String[] args) {
        int indice = 0;
        for (int indice = 0; indice < 2; indice++) System.out.println(indice);
    }
}

// Salida esperada:
// Error: variable indice is already defined
