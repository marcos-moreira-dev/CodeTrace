// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.2; ejemplo 8.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java PuntoColor.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class PuntoColor extends Punto { void borrar() { limpiar(); } }

// Salida esperada:
// Error: constructor Punto
