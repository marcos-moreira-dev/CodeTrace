// Tema:
// Este contrato declara medir con retorno int. Una implementación debe cumplir ese tipo de
// retorno.
// Compilar: javac --release 21 -encoding UTF-8 -d . Entera.java Larga.java RetornosInterfaz.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Entera { int medir(); }

// Salida esperada:
// La compilación conjunta se rechaza: unrelated return types
