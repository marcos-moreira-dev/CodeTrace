// Tema:
// Este contrato declara medir con retorno long. Los retornos primitivos int y long no son
// covariantes para sobrescritura.
// Compilar: javac --release 21 -encoding UTF-8 -d . Entera.java Larga.java RetornosInterfaz.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Larga { long medir(); }

// Salida esperada:
// La compilación conjunta se rechaza: unrelated return types
