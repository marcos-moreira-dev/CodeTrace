// Tema:
// Una anotación explícita fija el contrato estático que el valor debe satisfacer.

const v: { x: 1 | 2 | 3 } = {
    x: 1, // x is now a union of literal types: 1 | 2 | 3
};
v.x = 3; // Valid
v.x = 100; // Invalid
