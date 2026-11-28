// Tema: returntype t.
// ReturnType obtiene el tipo de retorno de una función.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Func = (name: string) => number;
type MyType = ReturnType<Func>; // number
