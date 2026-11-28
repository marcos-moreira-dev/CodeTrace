// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.12.2; ejemplo 15.12.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Doble.java Aplicabilidad.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Doble { private static int duplicar(int n) { return n * 2; } }

// Salida esperada:
// Error: has private access
