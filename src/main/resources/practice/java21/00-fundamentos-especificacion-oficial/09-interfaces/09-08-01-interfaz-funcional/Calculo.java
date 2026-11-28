// Tema:
// Una interfaz funcional tiene un único método abstracto efectivo. Una lambda proporciona su
// implementación según el tipo objetivo.
// Compilar: javac --release 21 -encoding UTF-8 -d . InterfazFuncional.java Calculo.java
// Ejecutar: java InterfazFuncional
// Comandos desde la carpeta de la unidad.

@FunctionalInterface
interface Calculo { int aplicar(int n); }

// Salida esperada:
// 10
