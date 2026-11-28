// Tema:
// var infiere un único tipo por declaración local. No admite múltiples declaradores
// separados por comas en la misma declaración.
// Compilar: javac --release 21 -encoding UTF-8 -d . VarMultiples.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class VarMultiples {
    void probar() { var a = 1, b = 2; }
}

// Salida esperada:
// Error: 'var' is not allowed in a compound declaration
