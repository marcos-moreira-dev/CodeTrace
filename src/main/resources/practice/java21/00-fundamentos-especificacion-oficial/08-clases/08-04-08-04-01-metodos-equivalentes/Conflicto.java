// Tema:
// Al parametrizar una superclase, dos métodos distintos pueden adquirir firmas equivalentes.
// Una subclase no puede heredar implementaciones incompatibles de esas firmas.
// Compilar: javac --release 21 -encoding UTF-8 -d . Generica.java Conflicto.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Conflicto extends Generica<String> {}

// Salida esperada:
// Error: same signature
