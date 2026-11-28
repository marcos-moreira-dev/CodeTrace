// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.2; ejemplo 6.5.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . biblioteca/Sonido.java cliente/Reclasificar.java
// Ejecutar: java cliente.Reclasificar
// Comandos desde la carpeta de la unidad.

package biblioteca;
public interface Sonido { int[] NOTAS = new int[4]; }

// Salida esperada:
// 4
