// Tema:
// El nombre calificador de un campo static no puede incluir argumentos de tipo: el campo es
// compartido y no depende de T.
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoParametrizado.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class CampoParametrizado<T> {
    static int total = 4;
    void probar() {
        CampoParametrizado<String>.total = 9;
    }
}

// Salida esperada:
// Error: not a statement
