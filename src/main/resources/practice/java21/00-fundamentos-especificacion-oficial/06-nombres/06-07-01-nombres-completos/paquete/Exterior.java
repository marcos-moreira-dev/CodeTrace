// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.7; ejemplo 6.7-1
// Compilar: javac --release 21 -encoding UTF-8 -d . paquete/Exterior.java NombresCompletos.java
// Ejecutar: java NombresCompletos
// Comandos desde la carpeta de la unidad.

package paquete;
public class Exterior { public static class Interior {} }

// Salida esperada:
// paquete.Exterior
// paquete.Exterior.Interior
