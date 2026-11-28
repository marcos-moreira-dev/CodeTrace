// Tema:
// La resolución de una invocación considera los métodos accesibles y aplicables a sus
// argumentos. Un método private de otra clase no puede invocarse directamente.
// Compilar: javac --release 21 -encoding UTF-8 -d . Doble.java Aplicabilidad.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Doble { private static int duplicar(int n) { return n * 2; } }

// Salida esperada:
// Error: has private access
