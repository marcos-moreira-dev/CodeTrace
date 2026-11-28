// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.8.7.1; ejemplo 8.8.7.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ConstructorInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class ConstructorInvalido {
    int valor = 5;
    ConstructorInvalido() { this(valor); }
    ConstructorInvalido(int n) { valor = n; }
}

// Salida esperada:
// Error: cannot reference valor before supertype constructor has been called
