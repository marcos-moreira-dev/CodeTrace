// Tema:
// Una interfaz genérica puede ser funcional en unas parametrizaciones y no en otras. Los
// retornos String e Integer no pueden satisfacerse con una sola implementación de la firma.
// Compilar: javac --release 21 -encoding UTF-8 -d . RetornosGenericos.java
// ERROR INTENCIONAL de compilación. No ejecutar.

interface General { Object aplicar(Class c); }
interface Primero<S> { S aplicar(Class<?> c); }
interface Segundo<T> { T aplicar(Class<?> c); }
interface Ambos<S,T> extends General, Primero<S>, Segundo<T> {}
class RetornosGenericos {
    Ambos<String,Integer> f = c -> "texto";
}

// Salida esperada:
// Error: incompatible
