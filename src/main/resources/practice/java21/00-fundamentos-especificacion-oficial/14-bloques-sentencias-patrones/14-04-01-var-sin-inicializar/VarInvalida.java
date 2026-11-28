// Tema:
// Una declaración local con var necesita un inicializador que permita inferir su tipo. Una
// declaración sin inicializador debe rechazarse.
// Compilar: javac --release 21 -encoding UTF-8 -d . VarInvalida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class VarInvalida {
    public static void main(String[] args) {
        var dato;
        System.out.println(dato);
    }
}

// Salida esperada:
// Error: cannot infer type for local variable dato
