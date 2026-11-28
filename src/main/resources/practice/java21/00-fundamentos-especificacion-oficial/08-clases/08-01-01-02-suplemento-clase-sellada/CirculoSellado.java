// Tema:
// final cierra esta rama de la jerarquía: no se permiten subclases del círculo.
// Compilar: javac --release 21 -encoding UTF-8 -d . FiguraSellada.java CirculoSellado.java FiguraLibre.java ClasesSelladas.java
// Ejecutar: java ClasesSelladas
// Comandos desde la carpeta de la unidad.

final class CirculoSellado extends FiguraSellada {}

// Salida esperada:
// true
// false
// false
