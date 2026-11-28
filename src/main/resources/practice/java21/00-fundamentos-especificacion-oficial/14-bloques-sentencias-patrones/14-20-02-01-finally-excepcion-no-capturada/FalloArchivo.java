// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.20.2; ejemplo 14.20.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . FalloArchivo.java FinallyNoCapturada.java
// Ejecutar: java FinallyNoCapturada
// Comandos desde la carpeta de la unidad.

class FalloArchivo extends Exception {}

// Salida esperada:
// limpieza
// Después: NullPointerException (intencional).
