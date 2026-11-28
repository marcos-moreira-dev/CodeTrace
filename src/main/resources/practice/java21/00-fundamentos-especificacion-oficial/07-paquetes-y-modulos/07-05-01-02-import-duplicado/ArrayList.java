// Tema:
// Una importación de tipo simple no puede introducir un nombre que entre en conflicto con una
// declaración superior de la misma unidad de compilación.
// Compilar: javac --release 21 -encoding UTF-8 -d . ArrayList.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

import java.util.ArrayList;
class ArrayList {}

// Salida esperada:
// Error: ArrayList is already defined
