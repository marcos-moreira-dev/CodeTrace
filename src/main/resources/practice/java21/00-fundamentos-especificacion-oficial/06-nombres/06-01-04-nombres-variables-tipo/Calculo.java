// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.1; ejemplo 6.1-4
// Compilar: javac --release 21 -encoding UTF-8 -d . Contenedor.java Par.java Calculo.java NombresGenericos.java
// Ejecutar: java NombresGenericos
// Comandos desde la carpeta de la unidad.

interface Calculo<T, X extends Throwable> { T evaluar() throws X; }

// Salida esperada:
// texto
// clave 7
