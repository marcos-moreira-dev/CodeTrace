// Tema: explicit type annotation.
// Una anotación explícita fija el contrato estático que el valor debe satisfacer.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Diagnóstico esperado: TS2322; no ejecutar.

const v: { x: 1 | 2 | 3 } = {
    x: 1, // x is now a union of literal types: 1 | 2 | 3
};
v.x = 3; // Válido
v.x = 100; // Inválido
