// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.8; ejemplo 9.8-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java FuncionA.java FuncionB.java Funcion.java FuncionParametrizada.java
// Ejecutar: java FuncionParametrizada
// Tema JLS: T es un segundo tipo de retorno. Si S y T son incompatibles, no existe una implementación única que satisfaga ambos contratos.
// Comandos desde la carpeta de la unidad.

interface FuncionB<T> { T aplicar(Class<?> c); }

// Salida esperada:
// Integer
