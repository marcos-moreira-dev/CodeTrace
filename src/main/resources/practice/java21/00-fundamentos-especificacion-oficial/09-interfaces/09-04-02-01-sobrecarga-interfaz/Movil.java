// Tema:
// Una interfaz puede heredar o declarar métodos del mismo nombre con parámetros diferentes.
// Una clase concreta debe implementar todas las firmas abstractas requeridas.
// Compilar: javac --release 21 -encoding UTF-8 -d . Movil.java MovilPreciso.java SobrecargaInterfaz.java
// Ejecutar: java SobrecargaInterfaz
// Comandos desde la carpeta de la unidad.

interface Movil { void mover(int n); }

// Salida esperada:
// int
// float
// double
