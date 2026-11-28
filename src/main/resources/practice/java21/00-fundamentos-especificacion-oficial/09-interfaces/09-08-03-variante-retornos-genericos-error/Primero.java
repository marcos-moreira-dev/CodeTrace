// Tema:
// S determina el retorno que debe satisfacer este contrato cuando se parametriza la
// interfaz.
// Compilar: javac --release 21 -encoding UTF-8 -d . General.java Primero.java Segundo.java Ambos.java RetornosGenericos.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Primero<S> { S aplicar(Class<?> c); }

// Salida esperada:
// La compilación conjunta se rechaza: not compatible
