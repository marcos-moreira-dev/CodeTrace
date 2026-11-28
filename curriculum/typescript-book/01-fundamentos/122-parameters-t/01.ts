// Tema:
// Parameters obtiene una tupla con los tipos de parámetros de una función.

type Func = (a: string, b: number) => void;
type MyType = Parameters<Func>; // [a: string, b: number]
