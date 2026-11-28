// Tema:
// permits enumera las subclases directas autorizadas. Otra clase no puede extender esta
// raíz.
// Compilar: javac --release 21 -encoding UTF-8 -d . RaizSellada.java Autorizada.java NoAutorizada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

sealed class RaizSellada permits Autorizada {}

// Salida esperada:
// Error: not allowed to extend sealed class
