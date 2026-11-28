// Tema: type from func return.
// ReturnType obtiene el tipo de retorno declarado o inferido de una función.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const add = (x: number, y: number) => x + y; // TypeScript can infer that the return type of the function is a number
