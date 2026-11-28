// Tema: optional variance annotations for type parameters.
// in y out permiten expresar restricciones de varianza de parámetros de tipo en las declaraciones compatibles.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Producer<out T> = () => T;
type Consumer<in T> = (value: T) => void;
type Transformador<in out T> = (value: T) => T;
