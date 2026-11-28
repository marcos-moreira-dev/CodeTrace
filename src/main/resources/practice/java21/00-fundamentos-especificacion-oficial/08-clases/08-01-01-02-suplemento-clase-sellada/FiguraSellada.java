// Tema:
// sealed restringe las subclases directas a los tipos enumerados en permits.
// Compilar: javac --release 21 -encoding UTF-8 -d . FiguraSellada.java CirculoSellado.java FiguraLibre.java ClasesSelladas.java
// Ejecutar: java ClasesSelladas
// Comandos desde la carpeta de la unidad.

sealed abstract class FiguraSellada permits CirculoSellado, FiguraLibre {}

// Salida esperada:
// true
// false
// false
