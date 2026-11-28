// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.3.1; ejemplo 8.4.3.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseAbstracta.java Refinada.java AbstractoRefinado.java
// Ejecutar: java AbstractoRefinado
// Comandos desde la carpeta de la unidad.

abstract class Refinada extends BaseAbstracta { abstract Integer obtener(); }

// Salida esperada:
// 5
