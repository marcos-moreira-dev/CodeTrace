// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3; ejemplo 8.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoBase.java CampoContrato.java CampoAmbiguo.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface CampoContrato { int numero = 7; }

// Salida esperada:
// Error: reference to numero is ambiguous
