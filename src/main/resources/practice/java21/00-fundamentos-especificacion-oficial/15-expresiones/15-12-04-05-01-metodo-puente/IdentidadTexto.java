// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.12.4.5; ejemplo 15.12.4.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Identidad.java IdentidadTexto.java MetodoPuente.java
// Ejecutar: java MetodoPuente
// Comandos desde la carpeta de la unidad.

class IdentidadTexto extends Identidad<String> { String devolver(String x) { return x; } }

// Salida esperada:
// Sin salida estándar.
// Después: ClassCastException (intencional).
