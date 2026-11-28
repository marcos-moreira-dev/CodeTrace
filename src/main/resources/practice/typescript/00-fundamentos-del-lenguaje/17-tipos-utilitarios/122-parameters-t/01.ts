// Tema: parameters t.
// Parameters obtiene una tupla con los tipos de parámetros de una función.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Func = (a: string, b: number) => void;
type MyType = Parameters<Func>; // [a: string, b: number]
