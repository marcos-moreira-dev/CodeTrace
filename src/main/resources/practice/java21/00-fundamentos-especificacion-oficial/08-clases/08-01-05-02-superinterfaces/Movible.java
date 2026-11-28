// Tema:
// Una interfaz puede extender otras interfaces. Una clase concreta que la implementa debe
// satisfacer los métodos abstractos heredados de toda la jerarquía.
// Compilar: javac --release 21 -encoding UTF-8 -d . Movible.java Coloreable.java Pintable.java Punto.java PuntoColor.java Superinterfaces.java
// Ejecutar: java Superinterfaces
// Comandos desde la carpeta de la unidad.

interface Movible { void mover(int n); }

// Salida esperada:
// 3 5 mate
