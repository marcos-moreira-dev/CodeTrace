// Tema:
// El método abstracto de una interfaz funcional puede declarar sus propios parámetros de tipo.
// Una referencia a método genérico puede satisfacer ese contrato.
// Compilar: javac --release 21 -encoding UTF-8 -d . FuncionA.java FuncionB.java FuncionAB.java FuncionGenerica.java
// Ejecutar: java FuncionGenerica
// Comandos desde la carpeta de la unidad.

interface FuncionA { <E extends Exception> Object llamar() throws E; }

// Salida esperada:
// genérico
