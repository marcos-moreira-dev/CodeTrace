// Tema:
// Dos sentencias if independientes no permiten al análisis de asignación definida deducir
// necesariamente que sus ramas se excluyen. El compilador no puede garantizar que una
// variable final esté sin asignar antes de la segunda asignación.
// Compilar: javac --release 21 -encoding UTF-8 -d . FinalNoDemostrado.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class FinalNoDemostrado {
    static void probar(boolean bandera) {
        final int n;
        if (bandera) n = 3;
        if (!bandera) n = 4;
    }
}

// Salida esperada:
// Error: variable n might already have been assigned
