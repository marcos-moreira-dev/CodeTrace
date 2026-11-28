// Tema:
// Una interfaz genérica puede tener parametrizaciones con retornos compatibles y otras que no
// lo son. Aquí String satisface ambos contratos; la implementación explícita utiliza la firma
// con Class raw.
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Comandos desde la carpeta de la unidad.

interface Funcion<S,T> extends Base, FuncionA<S>, FuncionB<T> {}

// Salida esperada:
// Integer
