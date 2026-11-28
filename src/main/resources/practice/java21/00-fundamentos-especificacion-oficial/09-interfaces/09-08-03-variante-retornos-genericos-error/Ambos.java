// Tema:
// Esta interfaz reúne tres contratos. Su carácter funcional depende de los argumentos
// concretos de S y T; String e Integer no ofrecen un retorno común sustituible.
// Compilar: javac --release 21 -encoding UTF-8 -d . General.java Primero.java Segundo.java Ambos.java RetornosGenericos.java
// ERROR INTENCIONAL de compilación de la unidad. No ejecutar.
// Comandos desde la carpeta de la unidad.

interface Ambos<S,T> extends General, Primero<S>, Segundo<T> {}

// Salida esperada:
// La compilación conjunta se rechaza: not compatible
