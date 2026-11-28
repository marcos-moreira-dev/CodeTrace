// Tema:
// Una clase superior sin public solo es accesible dentro de su paquete. Un cliente de otro
// paquete no puede declarar una variable usando ese tipo.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java puntos/Lista.java cliente/Acceso.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

package cliente;
class Acceso { puntos.Punto p; puntos.Lista lista; }

// Salida esperada:
// Error: is not public
