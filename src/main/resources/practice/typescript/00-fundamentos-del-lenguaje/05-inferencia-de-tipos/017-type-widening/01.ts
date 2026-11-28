// Tema: type widening.
// Un literal puede ampliarse a un tipo más general al inferir una variable mutable.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado: TS2322; no ejecutar.

let x = 'x'; // TypeScript infers as string, a wide type
let y: 'y' | 'x' = 'y'; // y types is a union of literal types
y = x; // Inválido Type 'string' is not assignable to type '"x" | "y"'.
