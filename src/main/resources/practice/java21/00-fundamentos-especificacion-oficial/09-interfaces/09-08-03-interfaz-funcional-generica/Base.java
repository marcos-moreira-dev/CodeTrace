// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Tema JLS: La firma con Class sin parámetro permite estudiar la relación entre métodos con firmas borradas y retornos sustituibles.
// Comandos desde la carpeta de la unidad.

interface Base { Object aplicar(Class c); }

// Salida esperada:
// Integer
