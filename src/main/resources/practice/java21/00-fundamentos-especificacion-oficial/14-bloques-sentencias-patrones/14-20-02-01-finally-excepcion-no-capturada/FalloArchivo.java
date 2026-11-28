// Tema:
// finally se ejecuta también cuando una excepción sale del try sin ser capturada. Si finally
// termina normalmente, la excepción original continúa propagándose.
// Compilar: javac --release 21 -encoding UTF-8 -d . FalloArchivo.java FinallyNoCapturada.java
// Ejecutar: java FinallyNoCapturada
// Comandos desde la carpeta de la unidad.

class FalloArchivo extends Exception {}

// Salida esperada:
// limpieza
// Después: NullPointerException (intencional).
