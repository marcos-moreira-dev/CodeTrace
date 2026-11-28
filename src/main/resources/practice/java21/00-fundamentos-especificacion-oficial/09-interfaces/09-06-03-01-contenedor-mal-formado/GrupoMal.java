// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.6.3; ejemplo 9.6.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Repetible.java GrupoMal.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

@interface GrupoMal { String[] value(); }

// Salida esperada:
// Error: el contenedor debe declarar value() con tipo Repetible[].
