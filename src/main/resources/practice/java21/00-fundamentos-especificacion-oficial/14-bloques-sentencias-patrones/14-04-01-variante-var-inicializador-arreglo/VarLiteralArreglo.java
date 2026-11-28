// Tema:
// Un inicializador de arreglo sin new necesita un tipo objetivo. var no proporciona ese
// tipo, por lo que no puede inferirse de una lista de valores entre llaves.
// Compilar: javac --release 21 -encoding UTF-8 -d . VarLiteralArreglo.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class VarLiteralArreglo {
    void probar() { var datos = {1, 2}; }
}

// Salida esperada:
// Error: array initializer needs an explicit target-type
