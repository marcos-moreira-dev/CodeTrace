// Tema:
// No se puede redeclarar una variable local con el mismo nombre dentro de un alcance local
// superpuesto. El compilador rechaza la segunda declaración.
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
