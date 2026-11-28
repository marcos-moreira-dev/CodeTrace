// Tema: more advanced inferences.
// La inferencia puede obtener un tipo común, una unión o un tipo contextual según las expresiones y el contexto de uso.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

let x = [new RegExp('x'), new Date()]; // Type inferred is: (RegExp | Date)[]
