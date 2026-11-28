// Tema: array.
// Un arreglo contiene elementos de un tipo determinado; puede declararse con T[] o Array<T>.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const x: [string, number] = ['a', 1];
const y: readonly [string, number] = ['a', 1];
