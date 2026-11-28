// Tema:
// clone es protected en Object, no public. Por eso declarar clone y otro método abstracto
// produce dos contratos abstractos efectivos y la interfaz no es funcional.
// Compilar: javac --release 21 -encoding UTF-8 -d . ClonarYMedir.java
// ERROR INTENCIONAL de compilación. No ejecutar.

@FunctionalInterface
interface ClonarYMedir {
    Object clone();
    int medir();
}

// Salida esperada:
// Error: multiple non-overriding abstract methods
