// Tema:
// Una invocación explícita a otro constructor no puede utilizar campos de la instancia que aún
// no ha sido inicializada. Se rechaza el acceso prematuro.
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
