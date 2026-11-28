// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.9; ejemplo 9.9-2
// Compilar: javac --release 21 -encoding UTF-8 -d . FuncionA.java FuncionB.java FuncionAB.java FuncionGenerica.java
// Ejecutar: java FuncionGenerica
// Comandos desde la carpeta de la unidad.

interface FuncionB { <F extends Exception> String llamar() throws Exception; }

// Salida esperada:
// genérico
