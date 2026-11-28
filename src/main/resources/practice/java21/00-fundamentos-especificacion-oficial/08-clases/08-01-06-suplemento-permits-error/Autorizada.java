// Tema:
// Esta clase está incluida en permits y cierra su rama mediante final.
// Compilar: javac --release 21 -encoding UTF-8 -d . RaizSellada.java Autorizada.java NoAutorizada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

final class Autorizada extends RaizSellada {}

// Salida esperada:
// Error: not allowed to extend sealed class
