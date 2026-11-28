// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.9; ejemplo 8.4.9-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java PuntoReal.java
// Ejecutar: java PuntoReal
// Comandos desde la carpeta de la unidad.

class Punto { int x, y; void mover(int dx,int dy) { x += dx; y += dy; } }

// Salida esperada:
// 2.5 3.5
// 0 0
