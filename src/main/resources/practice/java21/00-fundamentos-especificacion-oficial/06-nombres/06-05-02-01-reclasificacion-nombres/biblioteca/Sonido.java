// Tema:
// Un nombre calificado se interpreta por segmentos. Un segmento puede identificar un paquete,
// otro un tipo y los siguientes campos o expresiones.
// Compilar: javac --release 21 -encoding UTF-8 -d . biblioteca/Sonido.java cliente/Reclasificar.java
// Ejecutar: java cliente.Reclasificar
// Comandos desde la carpeta de la unidad.

package biblioteca;
public interface Sonido { int[] NOTAS = new int[4]; }

// Salida esperada:
// 4
