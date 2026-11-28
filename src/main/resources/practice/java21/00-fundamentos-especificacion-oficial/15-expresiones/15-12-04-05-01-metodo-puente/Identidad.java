// Tema:
// El borrado genérico puede requerir un método puente generado por el compilador. Una llamada
// mediante raw puede fallar al convertir un argumento al tipo específico que exige la
// sobrescritura.
// Compilar: javac --release 21 -encoding UTF-8 -d . Identidad.java IdentidadTexto.java MetodoPuente.java
// Ejecutar: java MetodoPuente
// Comandos desde la carpeta de la unidad.

abstract class Identidad<T> { abstract T devolver(T x); }

// Salida esperada:
// Sin salida estándar.
// Después: ClassCastException (intencional).
