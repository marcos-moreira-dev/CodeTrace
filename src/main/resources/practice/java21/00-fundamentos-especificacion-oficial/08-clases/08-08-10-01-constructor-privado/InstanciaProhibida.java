// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.8.10; ejemplo 8.8.10-1
// Compilar: javac --release 21 -encoding UTF-8 -d . SoloClase.java InstanciaProhibida.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class InstanciaProhibida { SoloClase objeto = new SoloClase(); }

// Salida esperada:
// Error: has private access
