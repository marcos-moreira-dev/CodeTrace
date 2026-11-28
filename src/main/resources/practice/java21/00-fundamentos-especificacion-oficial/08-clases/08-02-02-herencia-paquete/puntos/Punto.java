// Tema:
// Los miembros sin modificador están disponibles dentro de su paquete. Cambiar de paquete en
// una subclase restringe el acceso a esos miembros.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java puntos/Punto3D.java otro/Punto4D.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package puntos;
public class Punto { int x; }

// Salida esperada:
// Error: is not public
