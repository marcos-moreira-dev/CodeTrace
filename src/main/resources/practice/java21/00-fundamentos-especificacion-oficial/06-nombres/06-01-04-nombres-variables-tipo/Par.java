// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.1; ejemplo 6.1-4
// Compilar: javac --release 21 -encoding UTF-8 -d . Contenedor.java Par.java Calculo.java NombresGenericos.java
// Ejecutar: java NombresGenericos
// Comandos desde la carpeta de la unidad.

record Par<K,V>(K clave, V valor) {}

// Salida esperada:
// texto
// clave 7
