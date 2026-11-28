// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-1
// Compilar: javac --release 21 -encoding UTF-8 -d . InterfazFuncional.java Calculo.java
// Ejecutar: java InterfazFuncional
// Comandos desde la carpeta de la unidad.

@FunctionalInterface
interface Calculo { int aplicar(int n); }

// Salida esperada:
// 10
