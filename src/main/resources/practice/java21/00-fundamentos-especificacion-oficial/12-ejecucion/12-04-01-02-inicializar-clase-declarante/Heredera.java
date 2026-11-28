// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §12.4.1; ejemplo 12.4.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Declarante.java Heredera.java InicializarDeclarante.java
// Ejecutar: java InicializarDeclarante
// Comandos desde la carpeta de la unidad.

class Heredera extends Declarante { static { System.out.println("heredera"); } }

// Salida esperada:
// 43
