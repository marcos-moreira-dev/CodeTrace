// Tema:
// Dos métodos con la misma firma no pueden combinar retornos primitivos incompatibles. int
// y long no constituyen retornos sustituibles para sobrescritura.
// Compilar: javac --release 21 -encoding UTF-8 -d . Entera.java Larga.java RetornosInterfaz.java
// ERROR INTENCIONAL de compilación. No ejecutar.

interface RetornosInterfaz extends Entera, Larga {}

// Salida esperada:
// Error: unrelated return types
