// Tema:
// Una clase abstracta tampoco puede declarar métodos con firmas equivalentes y retornos
// incompatibles con su interfaz. La ausencia de implementación no elimina esa restricción.
// Compilar: javac --release 21 -encoding UTF-8 -d . Actualizable.java Actualizacion.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

abstract class Actualizacion implements Actualizable {
    public abstract int actualizar(int n);
}

// Salida esperada:
// Error: return type int is not compatible with void
