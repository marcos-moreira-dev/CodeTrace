// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.2; ejemplo 8.2-5
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java interno/PuntoEspecial.java interno/Creacion.java cliente/Invocar.java
// Ejecutar: java cliente.Invocar
// Comandos desde la carpeta de la unidad.

package interno;
class PuntoEspecial extends puntos.Punto { public void mover() { System.out.println("especial"); } }

// Salida esperada:
// especial
