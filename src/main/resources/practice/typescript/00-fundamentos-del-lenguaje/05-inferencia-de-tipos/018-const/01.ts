// Tema: const.
// const impide reasignar una variable y puede conservar un tipo literal más preciso; no hace inmutable cualquier objeto referenciado.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const x = 'x'; // TypeScript infers the type of x as 'x', a narrower type
let y: 'y' | 'x' = 'y';
y = x; // Válido: The type of x is inferred as 'x'
