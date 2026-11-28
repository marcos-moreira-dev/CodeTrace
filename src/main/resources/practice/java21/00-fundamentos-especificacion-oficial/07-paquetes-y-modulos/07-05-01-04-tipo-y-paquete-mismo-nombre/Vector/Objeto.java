// Tema:
// Un segmento de nombre puede identificar un paquete en un contexto y un tipo en otro. Las
// declaraciones import permiten resolver nombres calificados según su posición.
// Compilar: javac --release 21 -encoding UTF-8 -d . Vector/Objeto.java cliente/ImportarCoincidente.java
// Ejecutar: java cliente.ImportarCoincidente
// Comandos desde la carpeta de la unidad.

package Vector;
public class Objeto {}

// Salida esperada:
// java.util.Vector
// Vector.Objeto
