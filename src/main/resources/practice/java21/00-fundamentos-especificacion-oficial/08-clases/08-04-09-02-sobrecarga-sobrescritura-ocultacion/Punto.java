// Tema:
// La sobrecarga se resuelve al compilar; la sobrescritura de instancia se despacha en
// ejecución. Los campos se seleccionan por el tipo estático y pueden quedar ocultos.
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java PuntoReal.java
// Ejecutar: java PuntoReal
// Comandos desde la carpeta de la unidad.

class Punto { int x, y; void mover(int dx,int dy) { x += dx; y += dy; } }

// Salida esperada:
// 2.5 3.5
// 0 0
