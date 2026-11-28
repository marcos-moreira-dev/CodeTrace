// Tema:
// Una anotación repetible requiere un contenedor con un elemento value de tipo arreglo de la
// anotación repetida. Un retorno incompatible debe rechazarse.
// Compilar: javac --release 21 -encoding UTF-8 -d . Repetible.java GrupoMal.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

import java.lang.annotation.Repeatable;
@Repeatable(GrupoMal.class)
@interface Repetible {}

// Salida esperada:
// Error: el contenedor debe declarar value() con tipo Repetible[].
