// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.2; ejemplo 8.4.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . FirmasEquivalentes.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class FirmasEquivalentes {
    void guardar(java.util.List<String> datos) {}
    void guardar(java.util.List<Integer> datos) {}
}

// Salida esperada:
// Error: name clash
