// Tema:
// El inicializador de var debe permitir conocer su tipo sin utilizar la propia variable
// todavía no declarada. La autorreferencia impide la inferencia.
// Compilar: javac --release 21 -encoding UTF-8 -d . VarAutorreferencia.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class VarAutorreferencia {
    void probar() { var dato = (dato = 7); }
}

// Salida esperada:
// Error: cannot use 'var' on self-referencing variable
