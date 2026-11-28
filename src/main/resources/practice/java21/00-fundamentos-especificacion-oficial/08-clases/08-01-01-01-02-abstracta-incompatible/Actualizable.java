// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.1.1; ejemplo 8.1.1.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Actualizable.java Actualizacion.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Actualizable { void actualizar(int n); }

// Salida esperada:
// Error: return type int is not compatible with void
