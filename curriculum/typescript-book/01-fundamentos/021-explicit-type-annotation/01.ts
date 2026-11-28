// Tema:
// Una anotación explícita fija el contrato estático que el valor debe satisfacer.

const v = {
    x: 1, // Inferred type: number (widening)
};
v.x = 3; // Valid
