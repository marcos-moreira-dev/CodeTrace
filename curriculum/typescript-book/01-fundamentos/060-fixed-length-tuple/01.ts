// Tema:
// Una tupla de longitud fija exige una cantidad y orden de componentes compatibles con su
// declaración.

const x = [10, 'hello'] as const;
x.push(2); // Error
