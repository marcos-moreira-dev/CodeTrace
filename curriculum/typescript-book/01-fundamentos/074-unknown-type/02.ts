// Tema:
// unknown acepta valores de cualquier tipo, pero exige una comprobación o reducción antes de
// utilizarlos de forma específica.

const add = (a: unknown, b: unknown): number | undefined =>
    typeof a === 'number' && typeof b === 'number' ? a + b : undefined;
console.log(add(1, 2)); // 3
console.log(add('x', 2)); // undefined
