// Tema:
// T determina un segundo contrato de retorno. Dos tipos de retorno incompatibles impiden
// satisfacer ambos contratos con una sola implementación.
// Compilar: javac --release 21 -encoding UTF-8 -d . General.java Primero.java Segundo.java Ambos.java RetornosGenericos.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Segundo<T> { T aplicar(Class<?> c); }

// Salida esperada:
// La compilación conjunta se rechaza: not compatible
