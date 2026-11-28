// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Tema JLS: La declaración genérica puede ser funcional, pero no todas sus parametrizaciones lo son. Funcion<String,String> admite un retorno String; Funcion<String,Integer> no.
// Comandos desde la carpeta de la unidad.

interface Funcion<S,T> extends Base, FuncionA<S>, FuncionB<T> {}

// Salida esperada:
// Integer
