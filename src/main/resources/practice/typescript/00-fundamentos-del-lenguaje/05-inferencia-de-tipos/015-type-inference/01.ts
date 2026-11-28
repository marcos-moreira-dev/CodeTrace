// Tema: type inference.
// El compilador deduce tipos a partir de inicializadores, expresiones y retornos cuando no se escribe una anotación.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

let x = 'x'; // The type inferred is string
