// Tema:
// const impide reasignar una variable y puede conservar un tipo literal más preciso; no hace
// inmutable cualquier objeto referenciado.

const x = 'x'; // TypeScript infers the type of x as 'x', a narrower type
let y: 'y' | 'x' = 'y';
y = x; // Valid: The type of x is inferred as 'x'
