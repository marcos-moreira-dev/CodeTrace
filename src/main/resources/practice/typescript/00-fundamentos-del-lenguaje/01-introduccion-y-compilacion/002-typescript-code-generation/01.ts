// Tema: Comprobación antes de emitir
// Una firma numérica impide pasar cadenas. La comprobación estática y la emisión de JavaScript son operaciones distintas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2345). No ejecutar.

const add = (a: number, b: number): number => a + b;
const result = add('x', 'y');
