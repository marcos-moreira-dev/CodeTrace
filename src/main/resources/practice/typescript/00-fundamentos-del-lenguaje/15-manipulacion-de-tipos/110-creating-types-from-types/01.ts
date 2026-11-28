// Tema: creating types from types.
// Las operaciones sobre tipos existentes permiten construir contratos derivados sin repetir manualmente su estructura.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type A = { foo: number };
type B = { bar: string };
type C = A & B; // Intersection of A and B
const obj: C = { foo: 42, bar: 'hello' };
