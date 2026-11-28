// Tema:
// Con var, las dimensiones del arreglo deben deducirse del inicializador. No se pueden
// agregar corchetes al nombre de la variable.
// Compilar: javac --release 21 -encoding UTF-8 -d . VarArreglo.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class VarArreglo {
    void probar() { var datos[] = new int[2]; }
}

// Salida esperada:
// Error: 'var' is not allowed as an element type of an array
