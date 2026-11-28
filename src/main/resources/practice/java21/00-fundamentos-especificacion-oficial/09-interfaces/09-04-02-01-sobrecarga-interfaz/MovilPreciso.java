// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.4.2; ejemplo 9.4.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Movil.java MovilPreciso.java SobrecargaInterfaz.java
// Ejecutar: java SobrecargaInterfaz
// Comandos desde la carpeta de la unidad.

interface MovilPreciso extends Movil { void mover(float n); void mover(double n); }

// Salida esperada:
// int
// float
// double
