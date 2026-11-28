// Tema:
// ReturnType obtiene el tipo de retorno de una función.

type Func = (name: string) => number;
type MyType = ReturnType<Func>; // number
