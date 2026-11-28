// Tema:
// La sobrecarga se resuelve según los parámetros aplicables, no según el tipo de retorno que
// el cliente desea recibir. Una asignación incompatible puede fallar después de seleccionar el
// método.
// Compilar: javac --release 21 -encoding UTF-8 -d . Figura.java Circulo.java RetornoNoSelecciona.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class Circulo extends Figura {}

// Salida esperada:
// Error: int cannot be converted to String
