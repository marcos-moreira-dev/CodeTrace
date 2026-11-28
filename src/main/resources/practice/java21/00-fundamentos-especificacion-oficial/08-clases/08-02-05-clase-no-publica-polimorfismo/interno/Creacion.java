// Tema:
// Un cliente puede usar un objeto de clase no pública mediante una referencia a un supertipo
// público. La invocación de instancia selecciona la implementación real.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java interno/PuntoEspecial.java interno/Creacion.java cliente/Invocar.java
// Ejecutar: java cliente.Invocar
// Comandos desde la carpeta de la unidad.

package interno;
public class Creacion { public static puntos.Punto crear() { return new PuntoEspecial(); } }

// Salida esperada:
// especial
