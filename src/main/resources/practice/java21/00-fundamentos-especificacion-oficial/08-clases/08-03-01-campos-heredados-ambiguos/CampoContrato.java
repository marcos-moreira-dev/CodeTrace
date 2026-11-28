// Tema:
// Heredar campos distintos con el mismo nombre puede hacer ambiguo un nombre simple.
// Calificarlo con el tipo que declara el campo permite seleccionar el deseado.
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoBase.java CampoContrato.java CampoAmbiguo.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface CampoContrato { int numero = 7; }

// Salida esperada:
// Error: reference to numero is ambiguous
