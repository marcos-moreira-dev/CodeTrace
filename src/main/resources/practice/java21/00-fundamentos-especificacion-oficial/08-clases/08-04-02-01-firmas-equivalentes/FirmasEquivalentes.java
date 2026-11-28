// Tema:
// La firma de método no se distingue solo por los argumentos genéricos después del borrado.
// Dos declaraciones con la misma firma borrada pueden causar un conflicto de nombres.
// Compilar: javac --release 21 -encoding UTF-8 -d . FirmasEquivalentes.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class FirmasEquivalentes {
    void guardar(java.util.List<String> datos) {}
    void guardar(java.util.List<Integer> datos) {}
}

// Salida esperada:
// Error: name clash
