// Tema:
// La firma utiliza Class sin argumento de tipo y un retorno Object; permite examinar la
// relación entre firmas borradas y retornos sustituibles.
// Compilar: javac --release 21 -encoding UTF-8 -d . General.java Primero.java Segundo.java Ambos.java RetornosGenericos.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface General { Object aplicar(Class c); }

// Salida esperada:
// La compilación conjunta se rechaza: not compatible
