// Tema:
// Una interfaz genérica puede ser funcional en unas parametrizaciones y no en otras. Los
// retornos String e Integer no pueden satisfacerse con una sola implementación de la firma.
// Compilar: javac --release 21 -encoding UTF-8 -d . General.java Primero.java Segundo.java Ambos.java RetornosGenericos.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class RetornosGenericos {
    Ambos<String,Integer> f = c -> "texto";
}

// Salida esperada:
// Error: not compatible
