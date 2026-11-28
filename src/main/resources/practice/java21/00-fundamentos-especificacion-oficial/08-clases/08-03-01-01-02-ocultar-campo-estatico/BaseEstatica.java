// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.1.1; ejemplo 8.3.1.1-2
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseEstatica.java OcultarEstatico.java
// Ejecutar: java OcultarEstatico
// Comandos desde la carpeta de la unidad.

class BaseEstatica { static int dato = 3; }

// Salida esperada:
// 6.5 3
