// Tema:
// Varios métodos abstractos heredados pueden representar un solo contrato si sus firmas y
// retornos son compatibles. En ese caso la interfaz sigue siendo funcional.
// Compilar: javac --release 21 -encoding UTF-8 -d . PrimeraFuncion.java SegundaFuncion.java FuncionUnida.java FuncionalHeredada.java
// Ejecutar: java FuncionalHeredada
// Comandos desde la carpeta de la unidad.

interface SegundaFuncion { int medir(Iterable<String> dato); }

// Salida esperada:
// 3
