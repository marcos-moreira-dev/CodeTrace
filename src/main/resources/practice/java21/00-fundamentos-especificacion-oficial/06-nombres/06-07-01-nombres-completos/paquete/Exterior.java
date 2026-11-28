// Tema:
// El nombre calificado incluye el paquete y, para un tipo miembro, el tipo exterior. El nombre
// canónico identifica una declaración sin depender de una importación.
// Compilar: javac --release 21 -encoding UTF-8 -d . paquete/Exterior.java NombresCompletos.java
// Ejecutar: java NombresCompletos
// Comandos desde la carpeta de la unidad.

package paquete;
public class Exterior { public static class Interior {} }

// Salida esperada:
// paquete.Exterior
// paquete.Exterior.Interior
