// Tema:
// Un campo private sigue perteneciendo a la clase que lo declara. Una subclase puede invocar
// un método accesible que lo modifica, pero no acceder a él directamente.
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java Punto3D.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Punto { private static int total; void mover() { total++; } }

// Salida esperada:
// Error: has private access
