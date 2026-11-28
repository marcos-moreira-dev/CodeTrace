// Tema:
// Los tipos superiores de un paquete pueden referenciarse mutuamente. La resolución de sus
// nombres no depende del orden de compilación de sus archivos.
// Compilar: javac --release 21 -encoding UTF-8 -d . puntos/Punto.java puntos/Color.java puntos/Prueba.java
// Ejecutar: java puntos.Prueba
// Comandos desde la carpeta de la unidad.

package puntos;
class Color { Punto primero; }

// Salida esperada:
// true
