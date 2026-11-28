// Tema: user defined type guards.
// El compilador moderno puede inferir el predicado en filter. Un guard explícito comunica el contrato de forma reutilizable.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// a,c,d,f
// a,c,d,f

const data: (string | null)[] = ['a',null,'c','d',null,'f'];
const r1 = data.filter(x => x !== null);
const isValid = (item: string | null): item is string => item !== null;
const r2 = data.filter(isValid);
console.log(r1.join(','));
console.log(r2.join(','));
