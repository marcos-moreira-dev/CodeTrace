// Tema:
// Los constructores no se heredan y los miembros private no son accesibles a una subclase
// independiente. El constructor implícito también necesita un constructor de superclase
// accesible.
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java PuntoColor.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class PuntoColor extends Punto { void borrar() { limpiar(); } }

// Salida esperada:
// Error: constructor Punto
