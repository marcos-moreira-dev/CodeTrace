// Tema:
// in y out permiten expresar restricciones de varianza de parámetros de tipo en las
// declaraciones compatibles.

type Producer<out T> = () => T;
type Consumer<in T> = (value: T) => void;
type Transformer<in out T> = (value: T) => T;
