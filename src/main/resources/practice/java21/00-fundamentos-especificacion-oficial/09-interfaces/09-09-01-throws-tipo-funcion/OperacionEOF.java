// Tema:
// Al combinar métodos abstractos con distintas cláusulas throws, el tipo funcional solo
// permite las excepciones checked compatibles con todos los contratos heredados.
// Compilar: javac --release 21 -encoding UTF-8 -d . OperacionIO.java OperacionEOF.java OperacionComun.java TipoFuncion.java
// Ejecutar: java TipoFuncion
// Comandos desde la carpeta de la unidad.

interface OperacionEOF { void hacer() throws java.io.EOFException; }

// Salida esperada:
// fin
