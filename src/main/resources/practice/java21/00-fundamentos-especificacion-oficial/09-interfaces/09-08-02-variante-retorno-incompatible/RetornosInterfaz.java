// Tema:
// Dos métodos con la misma firma no pueden combinar retornos primitivos incompatibles. int
// y long no constituyen retornos sustituibles para sobrescritura.
// Compilar: javac --release 21 -encoding UTF-8 -d . RetornosInterfaz.java
// ERROR INTENCIONAL de compilación. No ejecutar.

interface Entera { int medir(); }
interface Larga { long medir(); }
interface RetornosInterfaz extends Entera, Larga {}

// Salida esperada:
// Error: incompatible return types
