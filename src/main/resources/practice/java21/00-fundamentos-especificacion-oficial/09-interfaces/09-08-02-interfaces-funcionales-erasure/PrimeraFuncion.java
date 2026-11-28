// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-2
// Compilar: javac --release 21 -encoding UTF-8 -d . PrimeraFuncion.java SegundaFuncion.java FuncionUnida.java FuncionalHeredada.java
// Ejecutar: java FuncionalHeredada
// Comandos desde la carpeta de la unidad.

interface PrimeraFuncion { int medir(Iterable<String> dato); }

// Salida esperada:
// 3
