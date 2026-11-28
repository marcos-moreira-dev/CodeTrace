// Tema:
// La accesibilidad de un tipo no garantiza la de su constructor. El constructor protected de
// una clase interna puede estar fuera del alcance de un cliente de otro paquete.
// Compilar: javac --release 21 -encoding UTF-8 -d . base/Exterior.java otro/Derivada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package base;
public class Exterior { protected class Interna {} }

// Salida esperada:
// Error: has protected access
