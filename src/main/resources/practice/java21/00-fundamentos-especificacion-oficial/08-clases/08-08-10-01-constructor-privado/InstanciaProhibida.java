// Tema:
// Un constructor private impide crear instancias directamente desde clases ajenas. La propia
// clase puede controlar cómo se construyen sus objetos.
// Compilar: javac --release 21 -encoding UTF-8 -d . SoloClase.java InstanciaProhibida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class InstanciaProhibida { SoloClase objeto = new SoloClase(); }

// Salida esperada:
// Error: has private access
