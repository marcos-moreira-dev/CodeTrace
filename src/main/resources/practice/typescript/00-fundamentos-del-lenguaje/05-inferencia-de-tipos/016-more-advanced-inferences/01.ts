// Tema: more advanced inferences.
// La inferencia puede obtener un tipo común, una unión o un tipo contextual según las expresiones y el contexto de uso.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

let x = [1, 'x', 1, null]; // The type inferred is: (string | number | null)[]
