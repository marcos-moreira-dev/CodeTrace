// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.5; ejemplo 8.1.5-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Movible.java Coloreable.java Pintable.java Punto.java PuntoColor.java Superinterfaces.java
// Ejecutar: java Superinterfaces
// Comandos desde la carpeta de la unidad.

class Punto implements Movible { int x; public void mover(int n) { x += n; } }

// Salida esperada:
// 3 5 mate
