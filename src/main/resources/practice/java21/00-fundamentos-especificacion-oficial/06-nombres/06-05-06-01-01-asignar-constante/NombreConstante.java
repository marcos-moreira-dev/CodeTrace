// Tema:
// Una variable final ya inicializada no puede volver a asignarse. El compilador verifica esta
// restricción incluso cuando la variable se usa por su nombre simple.
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
// Error: final variable LIMITE
