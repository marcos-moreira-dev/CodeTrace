// Tema:
// La clase intenta extender una superclase sellada sin estar en su lista permits. Debe
// rechazarse.
// Compilar: javac --release 21 -encoding UTF-8 -d . RaizSellada.java Autorizada.java NoAutorizada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

final class NoAutorizada extends RaizSellada {}

// Salida esperada:
// Error: not allowed to extend sealed class
