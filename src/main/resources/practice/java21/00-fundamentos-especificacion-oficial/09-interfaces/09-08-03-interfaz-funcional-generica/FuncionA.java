// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Tema JLS: S es el tipo del retorno de esta interfaz; la parametrización determina el retorno que debe satisfacer la implementación.
// Comandos desde la carpeta de la unidad.

interface FuncionA<S> { S aplicar(Class<?> c); }

// Salida esperada:
// Integer
