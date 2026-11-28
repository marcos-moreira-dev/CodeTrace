// Tema:
// Una importación de tipo simple debe nombrar un tipo existente. Para importar tipos
// accesibles de un paquete bajo demanda se utiliza la forma paquete.*.
// Compilar: javac --release 21 -encoding UTF-8 -d . ImportarPaquete.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

import java.util;
public class ImportarPaquete {}

// Salida esperada:
// Error: cannot find symbol
