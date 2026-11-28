// Tema: fixed length tuple.
// Una tupla de longitud fija exige una cantidad y orden de componentes compatibles con su declaración.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado: TS2339; no ejecutar.

const x = [10, 'hello'] as const;
x.push(2); // Error
