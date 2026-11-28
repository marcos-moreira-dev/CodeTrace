// Tema:
// Dos tipos superiores de un mismo paquete no pueden tener el mismo nombre simple, aunque
// estén en archivos diferentes o uno sea una interfaz.
// Compilar: javac --release 21 -encoding UTF-8 -d . a/Entidad.java a/Conflicto.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package a;
class Entidad {}

// Salida esperada:
// Error: duplicate class: a.Entidad
