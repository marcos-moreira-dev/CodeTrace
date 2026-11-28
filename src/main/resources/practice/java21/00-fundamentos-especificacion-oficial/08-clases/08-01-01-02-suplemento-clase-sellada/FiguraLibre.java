// Tema:
// non-sealed permite que esta rama vuelva a extenderse libremente, aunque su padre sea
// sealed.
// Compilar: javac --release 21 -encoding UTF-8 -d . FiguraSellada.java CirculoSellado.java FiguraLibre.java ClasesSelladas.java
// Ejecutar: java ClasesSelladas
// Comandos desde la carpeta de la unidad.

non-sealed class FiguraLibre extends FiguraSellada {}

// Salida esperada:
// true
// false
// false
