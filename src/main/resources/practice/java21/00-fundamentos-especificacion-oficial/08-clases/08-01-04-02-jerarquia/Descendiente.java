// Tema:
// Una clase derivada es subtipo de su superclase directa y de los antecesores de esta.
// instanceof comprueba esa relación sobre la instancia real.
// Compilar: javac --release 21 -encoding UTF-8 -d . Antepasado.java Intermedio.java Descendiente.java Jerarquia.java
// Ejecutar: java Jerarquia
// Comandos desde la carpeta de la unidad.

final class Descendiente extends Intermedio {}

// Salida esperada:
// true
// true
// true
